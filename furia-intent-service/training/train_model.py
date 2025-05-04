# training/train_model.py

import json, random, spacy
from spacy.training.example import Example
from spacy.util import minibatch, compounding
from pathlib import Path

# ── Configurações ─────────────────────────────────────────────────────────────
MODEL_NAME  = "intent_model"
DATA_PATH   = Path("training/data.json")
EPOCHS      = 200
PATIENCE    = 5
SEED        = 42

# ── Data Augmentation (Random Deletion) ────────────────────────────────────────
def augment(text, p=0.1):
    words = text.split()
    if len(words) < 2:
        return text
    return " ".join([w for w in words if random.random() > p])

random.seed(SEED)

# ── Carrega e prepara dados ────────────────────────────────────────────────────
raw = json.loads(Path(DATA_PATH).read_text(encoding="utf-8"))
labels = sorted({item["label"] for item in raw})
data  = [(t, {"cats": {lbl: float(lbl == lab) for lbl in labels}})
         for item in raw for t, lab in [(item["text"], item["label"])]]

# duplica com augmentation
data += [(augment(text), ann) for text, ann in data]

random.shuffle(data)
split = int(0.8 * len(data))
train_data, dev_data = data[:split], data[split:]

# ── Cria pipeline em branco e adiciona textcat ─────────────────────────────────
nlp = spacy.blank("pt")
textcat = nlp.add_pipe("textcat", last=True)
for lbl in labels:
    textcat.add_label(lbl)

optimizer = nlp.initialize(lambda: (
    Example.from_dict(nlp.make_doc(text), ann) for text, ann in train_data
))

# ── Treino com early stopping e checkpoints ────────────────────────────────────
best_score = 0.0
no_improve = 0

for epoch in range(1, EPOCHS + 1):
    losses = {}
    batches = minibatch(train_data, size=compounding(4.0, 32.0, 1.001))
    for batch in batches:
        examples = [Example.from_dict(nlp.make_doc(t), ann) for t, ann in batch]
        nlp.update(examples, sgd=optimizer, drop=0.3, losses=losses)

    dev_ex  = [Example.from_dict(nlp.make_doc(t), ann) for t, ann in dev_data]
    scores  = nlp.evaluate(dev_ex)
    f_score = scores["cats_score"] * 100

    print(f"Época {epoch}/{EPOCHS} — Loss: {losses['textcat']:.4f} — F-score: {f_score:.2f}%")

    if f_score > best_score:
        best_score = f_score
        no_improve = 0
        Path(MODEL_NAME).mkdir(exist_ok=True)
        nlp.to_disk(MODEL_NAME)
        print(f"→ Checkpoint salvo (F-score: {f_score:.2f}%)")
    else:
        no_improve += 1
        if no_improve >= PATIENCE:
            print(f"→ Early stopping após {PATIENCE} épocas sem melhoria.")
            break

print("Treino concluído.")
