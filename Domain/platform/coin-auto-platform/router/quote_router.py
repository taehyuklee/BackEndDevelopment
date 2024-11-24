from fastapi import APIRouter
from service.quote_api import *
from domain.request.pydantic_body.req_quotation_body import QuotationRequestBody

router = APIRouter(
    prefix="/quote"
)

@router.get("/coin-info", tags=["quote"])
async def get_quote_info(detail_yn: bool):
    return get_coin_info(detail_yn)

@router.get("/coin-list", tags=["quote"])
async def get_quote_market_list(detail_yn: bool, only_krw_yn: bool):
    return get_coin_market_list(detail_yn, only_krw_yn)

@router.get("/coin-price", tags=["quote"])
async def get_quote_price_info(quotation_request_body :QuotationRequestBody):
    return get_quotation(quotation_request_body)