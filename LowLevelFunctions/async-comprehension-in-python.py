import asyncio


async def task1():
    print("Task 1 started")
    await asyncio.sleep(5)  # 2초 동안 대기 (비동기)
    print("Task 1 finished")


async def task2():
    print("Task 2 started")
    await asyncio.sleep(1)  # 1초 동안 대기 (비동기)
    print("Task 2 finished")


async def main_with_await():
    print("Start with await")

    # await를 사용하여 하나씩 실행
    await task1()
    await task2()

    print("End with await")


async def main_with_create_task():
    print("Start with asyncio.create_task")

    # asyncio.create_task로 두 작업을 동시에 실행
    task1_task = asyncio.create_task(task1())
    task2_task = asyncio.create_task(task2())

    # 두 작업이 끝날 때까지 기다림
    await task1_task
    await task2_task

    print("End with asyncio.create_task")


# 실행
async def run():
    print("Running with await:")
    await main_with_await()

    print("\nRunning with asyncio.create_task:")
    await main_with_create_task()


# 이벤트 루프에서 실행
asyncio.run(run())
