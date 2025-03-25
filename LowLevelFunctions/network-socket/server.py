import socket

port: int = 8080
# 서버 Socket 생성 및 Listen 상태
# L3, L4 관련한 것들을 인자로 주게 된다, (IP, TCP 통신)
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(("127.0.0.1", 8000))
server.listen(1)

while True:
    print("Server is waiting for connection...")
    conn, addr = server.accept()
    print(f"Connected to {addr}")

    data = conn.recv(1024).decode()
    print({data})

    conn.close()
    print("Close Connection")
