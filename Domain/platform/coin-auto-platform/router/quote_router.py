from fastapi import APIRouter

from typing import Union

router = APIRouter(
    prefix="/quote"
)

@router.post("/question", tags=["quote"])
async def get_quote(question_data: QuestionDto):
    return service.createQuestion(question_data)