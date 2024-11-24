from pydantic import BaseModel, Field
from datetime import datetime

class QuotationRequestBody(BaseModel):
    market: str = "KRW-BTC"
    kr_nm: str = "비트코인"
    eg_nm: str = "Bitcoin"
    time_unit: str = "seconds"
    min_unit: int = 15
    count: int = 0
    date: datetime = datetime.now()
    only_krw_yn: bool = True

    def __str__(self):
        return f"{self.time_unit} {self.min_unit} {self.count} {self.date}"

    @property
    def market(self):
        return self.market

    @market.setter
    def market(self, market: str):
        self.market = market

    @property
    def kr_nm(self):
        return self.kr_nm

    @kr_nm.setter
    def kr_nm(self, kr_nm: str):
        self.kr_nm = kr_nm

    @property
    def eg_nm(self):
        return self.eg_nm

    @eg_nm.setter
    def eg_nm(self, eg_nm: str):
        self.kr_nm = eg_nm

    @property
    def time_unit(self):
        return self.time_unit

    @time_unit.setter
    def time_unit(self, time_unit: str):
        self.time_unit = time_unit

    @property
    def min_unit(self):
        return self.min_unit

    @min_unit.setter
    def min_unit(self, min_unit: int):
        self.min_unit = min_unit

    @property
    def count(self):
        return self.count

    @count.setter
    def count(self, count: int):
        self.count = count

    @property
    def date(self):
        return self.date

    @date.setter
    def date(self, date: datetime):
        self.date = date