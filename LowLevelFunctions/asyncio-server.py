import asyncio
import time

async def handle_task1(reader, writer):
    """Task 1을 비동기적으로 처리하는 핸들러"""
    print("Task 1 started")
    # await asyncio.sleep(5)  # 비동기 작업: I/O 대기
    total = 0
    for i in range(100000000000000000):
        total += i

    print(total)
    print("Task 1 finished")

    message = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n{'status': 200, 'message': 'Task 1 completed'}"
    writer.write(message.encode())
    await writer.drain()
    writer.close()

async def handle_task2(reader, writer):
    """Task 2를 비동기적으로 처리하는 핸들러"""
    print("Task 2 started")
    await asyncio.sleep(1)  # 비동기 작업
    print("Task 2 finished")

    message = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n{'status': 200, 'message': 'Task 2 completed'}"
    writer.write(message.encode())
    await writer.drain()
    writer.close()

async def handle_client(reader, writer):
    """HTTP 요청을 처리하는 핸들러"""
    data = await reader.read(100)
    message = data.decode()

    print(f"Received data: {message}")

    # 간단한 라우팅 처리: 요청 경로에 따라 다른 작업 처리
    if "/task1" in message:
        await handle_task1(reader, writer)
    elif "/task2" in message:
        await handle_task2(reader, writer)
    else:
        message = "HTTP/1.1 404 Not Found\r\nContent-Type: application/json\r\n\r\n{'status': 404, 'message': 'Not Found'}"
        writer.write(message.encode())
        await writer.drain()
        writer.close()

async def run_server():
    """서버를 실행하고 요청을 비동기적으로 처리"""
    server = await asyncio.start_server(handle_client, '127.0.0.1', 8000)

    addr = server.sockets[0].getsockname()
    print(f'Serving on {addr}')

    # 서버가 실행 중일 때 계속해서 요청을 처리
    async with server:
        await server.serve_forever()

# 서버 실행
if __name__ == "__main__":
    asyncio.run(run_server())
