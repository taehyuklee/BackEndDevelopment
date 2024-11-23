# install requests
# python -m pip install requests

import requests
import datetime
from domain.request.req_quotation_dto import QuotationRequestBody
# from domain.response.res_quotation_dto import

# 현재 날짜와 시간 가져오기
now = datetime.datetime.now()

# 원하는 형식으로 출력
formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')


def _convert_time_format(date : datetime):
     return date.strftime('%Y-%m-%d %H:%M:%S')


def get_quotation(quotation_dto: QuotationRequestBody):

    time_unit = quotation_dto.time_unit # seconds, minutes, days, weeks, months, years
    min_unit = quotation_dto.min_unit
    count = quotation_dto.count
    date = _convert_time_format(quotation_dto.date)


    # KRW-BTC 마켓에 2024년 10월 1일(UTC) 이전 초봉 1개를 요청
    url = "https://api.upbit.com/v1/candles/" + time_unit if time_unit != "minutes" else time_unit +  "/" + min_unit

    params = {
        'market': 'KRW-BTC',
        'count': count,
        'to': date
    }

    headers = {"accept": "application/json"}

    response = requests.get(url, params=params, headers=headers)

    # print(response.text)

    for row in response.json():
        UTC = row.get('candle_date_time_utc')
        Open = row.get('opening_price')
        High = row.get('high_price')
        Low = row.get('low_price')
        Close = row.get('trade_price')

        print(UTC, Open, High, Low, Close)


#

# KRW-BTC 마켓에 2024년 10월 1일(UTC) 이전 가장 최근 1분봉 1개를 요청
min_unit = "1"
url = "https://api.upbit.com/v1/candles/minutes/" + min_unit
params = {
    'market': 'KRW-BTC',
    'count': 50,
    'to': '2024-11-18 00:00:00'
}
headers = {"accept": "application/json"}

response = requests.get(url, params=params, headers=headers)

# print(response.text)
for row in response.json():
    UTC = row.get('candle_date_time_utc')
    Open = row.get('opening_price')
    High = row.get('high_price')
    Low = row.get('low_price')
    Close = row.get('trade_price')

    print(UTC, Open, High, Low, Close)




# 종목 조회
def get_coin_list():
    url = "https://api.upbit.com/v1/market/all?is_details=true"

    headers = {"accept": "application/json"}

    res = requests.get(url, headers=headers)

    return res.json()

    # print(res.text)


get_coin_list()


__all__ = ['get_coin_list', 'get_quotation']