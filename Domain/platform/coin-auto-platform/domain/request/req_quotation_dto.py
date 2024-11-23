import datetime

class QuotationRequestBody:

    __time_unit = "sec"
    __min_unit = 15
    __count = 0
    __date = datetime.datetime.now()

    def __init__(self, time_unit: str, min_unit: int, count: int, date: datetime):
        self.__time_unit: str = time_unit
        self.__min_unit: int = min_unit
        if 200 < count or count <= 0:
            raise ValueError('cout unit은 1부터 200까지 가능합니다')
        self.__count: int = count
        self.__date: datetime = date


    def __str__(self):
        return self.__time_unit + " " + str(self.__min_unit) + " " + str(self.__count) + " " + str(self.__date)

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