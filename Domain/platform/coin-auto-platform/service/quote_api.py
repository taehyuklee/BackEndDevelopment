# install requests
# python -m pip install requests

import requests
import datetime

# 현재 날짜와 시간 가져오기
now = datetime.datetime.now()

# 원하는 형식으로 출력
formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')



# KRW-BTC 마켓에 2024년 10월 1일(UTC) 이전 초봉 1개를 요청
url = "https://api.upbit.com/v1/candles/seconds"
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



# KRW-BTC 마켓에 2024년 10월 1일(UTC) 이전 가장 최근 1분봉 1개를 요청
url = "https://api.upbit.com/v1/candles/minutes/1"
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

url = "https://api.upbit.com/v1/market/all?is_details=true"

headers = {"accept": "application/json"}

res = requests.get(url, headers=headers)

res.json()

print(res.text)
