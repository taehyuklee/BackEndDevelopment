from fastapi import APIRouter
from service.quote_api import *
from domain.request.pydantic_body.req_quotation_body import QuotationRequestBody
from domain.request.pydantic_body.req_excel_body import ExcelRequestBody

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

@router.get("/coin-excel", tags=["quote"])
async def get_excel_coin(excel_request_body: ExcelRequestBody):
    return get_excel_list(excel_request_body)

@router.get("/coin-execution-price", tags=["quote"])
async def get_execution_price():
    return execution_price()
