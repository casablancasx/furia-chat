import spacy
from pathlib import Path
from typing import Dict, Union
import unicodedata

model_path = Path(__file__).parent.parent / "intent_model"
nlp = spacy.load(model_path)


def remove_accents(input_str: str) -> str:
    nfkd_form = unicodedata.normalize('NFD', input_str)
    return "".join([c for c in nfkd_form if not unicodedata.combining(c)])


def predict_intent(text: str) -> Dict[str, Union[str, float]]:
    text_lower_no_accents = remove_accents(text.lower())

    rule_based_intents = [
        ("COACH", ["coach", "treinador"]),
        ("PLAYERS", ["player", "players", "jogador", "jogadores"]),
        ("UPCOMING_MATCH", ["proximos jogos", "proxima partida", "proximos resultados", "proxima rodada"]),
        ("PREVIOUS_MATCH", ["ultimos jogos", "ultimo jogo", "ultima partida", "resultados", "ultimos resultados"]),
        ("TRANSFERS", ["transferencia", "transfer", "transferencias"]),
        ("LAST_NEWS", ["noticias"]),
        ("CHAMPIONSHIP", ["proximo campeonato", "proximo torneio", "proxima competicao", "proximos campeonatos"])
    ]

    for intent, keywords in rule_based_intents:
        if any(keyword in text_lower_no_accents for keyword in keywords):
            return {"intent": intent, "score": 1.0}

    doc = nlp(text)

    if doc.cats:
        predicted_intent = max(doc.cats, key=doc.cats.get)
        score = doc.cats[predicted_intent]
        return {"intent": predicted_intent.upper(), "score": score}
    else:
        return {"intent": "OUT_OF_DOMAIN", "score": 0.0}