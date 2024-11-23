import datetime
from typing import Any

# 결국 Pydantic Model을 사용할수 밖에 없다.
class QuotationRequestBody:

    __market = "KRW-BTC"
    __kr_nm = "비트코인"
    __eg_nm = "Bitcoin"
    __time_unit = "sec"
    __min_unit = 15
    __count = 0
    __date = datetime.datetime.now()

    def __init__(self, market: str, kr_nm: str, eg_nm: str, time_unit: str, min_unit: int, count: int, date: datetime):
        self.__market: str = market
        self.__kr_nm: str = kr_nm
        self.__eg_nm: str = eg_nm
        self.__time_unit: str = time_unit
        self.__min_unit: int = min_unit
        if 200 < count or count <= 0:
            raise ValueError('cout unit은 1부터 200까지 가능합니다')
        self.__count: int = count
        self.__date: datetime = date

    def __str__(self):
        return self.__time_unit + " " + str(self.__min_unit) + " " + str(self.__count) + " " + str(self.__date)

    @property
    def market(self):
        return self.__market

    @market.setter
    def market(self, market: str):
        self.__market = market

    @property
    def kr_nm(self):
        return self.__kr_nm

    @kr_nm.setter
    def kr_nm(self, kr_nm: str):
        self.__kr_nm = kr_nm

    @property
    def eg_nm(self):
        return self.__eg_nm

    @eg_nm.setter
    def eg_nm(self, eg_nm: str):
        self.__kr_nm = eg_nm

    @property
    def time_unit(self):
        return self.__time_unit

    @time_unit.setter
    def time_unit(self, time_unit: str):
        self.__time_unit = time_unit

    @property
    def min_unit(self):
        return self.__min_unit

    @min_unit.setter
    def min_unit(self, min_unit: int):
        self.__min_unit = min_unit

    @property
    def count(self):
        return self.__count

    @count.setter
    def count(self, count: int):
        self.__count = count

    @property
    def date(self):
        return self.__date

    @date.setter
    def date(self, date: datetime):
        self.__date = date