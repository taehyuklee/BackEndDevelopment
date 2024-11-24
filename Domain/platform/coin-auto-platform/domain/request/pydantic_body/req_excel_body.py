from pydantic import BaseModel, Field
from datetime import datetime

class ExcelRequestBody(BaseModel):
    time_unit: str = "seconds"
    min_unit: int = 15
    count: int = 0
    duration_num: int = 1
    only_krw_yn: bool = True

    def __str__(self):
        return f"{self.time_unit} {self.min_unit} {self.count} {self.duration_num} {self.only_krw_yn}"

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
    def duration_num(self):
        return self.duration_num

    @duration_num.setter
    def duration_num(self, duration_num: int):
        self.duration_num = duration_num
