from fastapi import APIRouter
from service.quote_api import *

router = APIRouter(
    prefix="/quote"
)

@router.get("/coin-list", tags=["quote"])
async def get_quote():
    return get_coin_list()


__all__ = [get_quote]