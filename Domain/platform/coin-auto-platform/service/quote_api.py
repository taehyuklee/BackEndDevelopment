import requests
import datetime
import pandas as pd
import time
from typing import List, Tuple
from domain.request.req_quotation_dto import QuotationRequestBody
# from domain.request.pydantic_body.req_quotation_body import QuotationRequestBody

# 현재 날짜와 시간 가져오기
now = datetime.datetime.now()

# 원하는 형식으로 출력
formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')


def _convert_time_format(date : datetime):
     return date.strftime('%Y-%m-%d %H:%M:%S')


def get_quotation(quotation_dto: QuotationRequestBody):
    # Router에서 넘어오는 정보는 Pydantic model이긴 함. 하지만 내부에서는 QuotationDto로 써야 해서 이렇게 hint를 글어놓은

    market = quotation_dto.market
    time_unit = quotation_dto.time_unit # seconds, minutes, days, weeks, months, years
    min_unit = quotation_dto.min_unit
    count = quotation_dto.count
    date = _convert_time_format(quotation_dto.date)


    # KRW-BTC 마켓에 2024년 10월 1일(UTC) 이전 초봉 1개를 요청
    base_ulr = "https://api.upbit.com/v1/candles/"
    time_url = time_unit if time_unit != "minutes" else time_unit +  "/" + str(min_unit)
    url = base_ulr + time_url

    params = {
        'market': market,
        'count': count,
        'to': date
    }

    headers = {"accept": "application/json"}

    res = requests.get(url, params=params, headers=headers)

    # print(response.text)

    return res.json()


# 종목 조회
def get_coin_info(detail_yn: bool = True):
    url = "https://api.upbit.com/v1/market/all?is_details=" + str(detail_yn)

    headers = {"accept": "application/json"}

    res = requests.get(url, headers=headers)

    return res.json()


def get_coin_market_list(detail_yn: bool = False):
    response_list: List[Tuple]= []
    url = "https://api.upbit.com/v1/market/all?is_details=" + str(detail_yn)

    headers = {"accept": "application/json"}

    res = requests.get(url, headers=headers)

    for row in res.json():
        market = row.get('market')
        kr_nm = row.get('korean_name')
        eg_nm = row.get('english_name')
        response_list.append((market, kr_nm, eg_nm))

    return response_list


def get_excel_list(time_unit: str, min_unit: int, count: int):
    market_info_list = get_coin_market_list(detail_yn=False)

    df = pd.DataFrame

    market_index = 0
    while True:
        market, kr_nm, eg_nm =  market_info_list[market_index]
        quotation = QuotationRequestBody(market, kr_nm, eg_nm, time_unit, min_unit, count)

        try:
            res = get_quotation(quotation)
            for row in res:
                kst = row.get('candle_date_time_kst')
                utc = row.get('candle_date_time_utc')
                opening_price = row.get('opening_price')
                hig_price = row.get('high_price')
                low_price = row.get('low_price')
                clos_price = row.get('trade_price')
                trade_price = row.get('candle_acc_trade_price')
                trade_volume = row.get('candle_acc_trade_volume')

            market_index+=1

        except:
            print("attribute error")
            time.sleep(0.5)


get_excel_list("minutes", 15, 30)

from datetime import datetime, timedelta

# 현재 시각
current_time = datetime.now()

# 15분씩 줄이기
for i in range(10):  # 10번 반복
    print(current_time)
    current_time -= timedelta(minutes=15)  # 15분 감소

# __all__ = ['get_coin_info', 'get_quotation']