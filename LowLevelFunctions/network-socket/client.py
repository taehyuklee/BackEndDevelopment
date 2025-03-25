import socket

# 클라이언트 (프로세스 1)
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect(("127.0.0.1", 8000))

message = "Hello, Server!"
client.send(message.encode())  # 메시지 전송
client.close()