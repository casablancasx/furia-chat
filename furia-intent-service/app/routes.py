from fastapi import APIRouter
from app.schemas import Message
from app.nlp_model import predict_intent

router = APIRouter()

@router.post("/intent")
def detect_intent(message: Message):
    response = predict_intent(message.text)
    print(response)
    return response
