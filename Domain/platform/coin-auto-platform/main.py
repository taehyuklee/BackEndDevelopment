# 출처: fastapi 공식문서
from typing import Union
from fastapi import FastAPI

app = FastAPI()


@app.get("/sync/say-hello")
def read_root():
    return {"Hello": "World"}

@app.get("/async/say-hello")
async def async_read_root():
    return {"async Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}
