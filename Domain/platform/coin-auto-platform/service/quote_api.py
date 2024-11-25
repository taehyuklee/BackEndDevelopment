import requests
from datetime import datetime, timedelta
import pandas as pd
import time
from typing import List, Tuple
from common.time_converter import *
from domain.request.req_quotation_dto import QuotationRequestBody
from domain.request.pydantic_body.req_excel_body import ExcelRequestBody
# from domain.request.pydantic_body.req_quotation_body import QuotationRequestBody

# 현재 날짜와 시간 가져오기
now = datetime.now()

# 원하는 형식으로 출력
formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')

def _convert_time_format(date : datetime, zone:str = "kr"):
    if zone == 'kr':
        return date.strftime('%Y-%m-%dT%H:%M:%S') +'+09:00'
    else:
        return date.strftime('%Y-%m-%dT%H:%M:%S')


def _insert_columns(coin_nm:str, coin_data_frame: dict[str, pd.DataFrame]):
    list_row = ['kst','opening_price','high_price','low_price','closing_price','cumulative_trade_price', 'cumulative_volume']
    if coin_nm not in coin_data_frame:
        df = pd.DataFrame()
        for content in list_row:
            header = coin_nm + "_" + content
            df[header] = None
        coin_data_frame[coin_nm] = df

def get_quotation(quotation_dto: QuotationRequestBody):
    # Router에서 넘어오는 정보는 Pydantic model이긴 함. 하지만 내부에서는 QuotationDto로 써야 해서 이렇게 hint를 글어놓은

    market = quotation_dto.market
    time_unit = quotation_dto.time_unit # seconds, minutes, days, weeks, months, years
    min_unit = quotation_dto.min_unit
    count = quotation_dto.count
    date = _convert_time_format(quotation_dto.date, 'kr')


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


def get_coin_market_list(detail_yn: bool = False, only_krw_yn: bool = True):
    response_list: List[Tuple]= []
    url = "https://api.upbit.com/v1/market/all?is_details=" + str(detail_yn)

    headers = {"accept": "application/json"}

    res = requests.get(url, headers=headers)

    for row in res.json():
        if only_krw_yn:
            if "KRW".lower() in  row.get('market').lower():
                market = row.get('market')
                kr_nm = row.get('korean_name')
                eg_nm = row.get('english_name')
                response_list.append((market, kr_nm, eg_nm))
        else:
            market = row.get('market')
            kr_nm = row.get('korean_name')
            eg_nm = row.get('english_name')
            response_list.append((market, kr_nm, eg_nm))

    return response_list


def get_excel_list(excel_request_body: ExcelRequestBody):

    print(excel_request_body)

    time_unit: str = excel_request_body.time_unit
    min_unit: int = excel_request_body.min_unit
    count: int = excel_request_body.count
    duration_num: int = excel_request_body.duration_num
    only_krw_yn: bool = excel_request_body.only_krw_yn

    market_info_list = get_coin_market_list(detail_yn=False, only_krw_yn=only_krw_yn)
    start_ref_time = datetime.now()
    cnt_time = duration_num*1440//min_unit-1

    coin_data_frame = {}

    market_index = 0
    print("make raw data excel")

    while True:
        if market_index ==5:
            break
        market, kr_nm, eg_nm =  market_info_list[market_index]
        ref_time = start_ref_time #처음에 시작한 시각을 받아오기
        time_index: int = 0 #time_index 초기화
        mid_ref_time: datetime = datetime.now()


        _insert_columns(market, coin_data_frame)

        while True:
            if time_index == cnt_time:
                break

            try:
                # ref_time -= timedelta(minutes= time_index * min_unit* count)
                quotation = QuotationRequestBody(market, kr_nm, eg_nm, time_unit, min_unit, count, ref_time)

                res = get_quotation(quotation)

                for row in res:
                    kst = row.get('candle_date_time_kst')
                    # utc = row.get('candle_date_time_utc')
                    opening_price = row.get('opening_price')
                    high_price = row.get('high_price')
                    low_price = row.get('low_price')
                    closing_price = row.get('trade_price')
                    trade_price = row.get('candle_acc_trade_price')
                    trade_volume = row.get('candle_acc_trade_volume')
                    mid_ref_time = datetime.fromisoformat(kst) #문자열을 다시 datetime formate으로 바꿔준다.
                    # print(kst, opening_price, high_price, low_price, closing_price, trade_price, trade_volume)

                    # DataFrame에 새 행 추가
                    coin_data_frame[market].loc[len(coin_data_frame[market])] = {
                        f"{market}_kst": kst,
                        # f"{market}_utc": utc,
                        f"{market}_opening_price": opening_price,
                        f"{market}_high_price": high_price,
                        f"{market}_low_price": low_price,
                        f"{market}_closing_price": closing_price,
                        f"{market}_cumulative_trade_price": trade_price,
                        f"{market}_cumulative_volume": trade_volume
                    }

                # 맨 아래 시간 update해줘야 한다.  # 맨 아래가 기준이 된다
                ref_time = mid_ref_time
                time_index +=1

                print(coin_data_frame)

            except Exception as e:
                print("An error occurred:", str(e))  # 예외 메시지를 출력
                print("Reference time:", ref_time)   # 현재 참조 시간도 출력:
                print("attribute error")
                time.sleep(0.2)

        market_index+=1

    final_df = pd.concat(coin_data_frame.values(), axis=1)

    print("final")
    print(final_df)
    final_df .to_csv("coin_data_v3.csv", index=False)


# get_excel_list("minutes", 60, 200, 1, True)


# 빈 DataFrame 생성
# df = pd.DataFrame()
#
# # 컬럼과 데이터 추가
# df['Column1'] = [1, 2, 3]
# df['Column2'] = ['A', 'B', 'C']
#
# print(df)df = pd.DataFrame()
#
# # 컬럼과 데이터 추가
# df['Column1'] = [1, 2, 3]
# df['Column2'] = ['A', 'B', 'C']
#
# print(df)

# # 현재 시각
# current_time = datetime.now()
#
# # 15분씩 줄이기
# for i in range(10):  # 10번 반복
#     print(current_time)
#     current_time -= timedelta(minutes=15)  # 15분 감소
#
# # __all__ = ['get_coin_info', 'get_quotation']

import requests

def execution_price():
    url = "https://api.upbit.com/v1/trades/ticks"

    headers = {"accept": "application/json"}

    params = {
        'market': 'KRW-BTC',
        'count': 200
    }
    res = requests.get(url, params=params, headers=headers)

    print(res.text)

    return res.json()
