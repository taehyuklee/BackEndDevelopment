import java.net.*;

public class PacketTracer {
	
	
    public static void main(String[] args) throws Exception {
    	
        DatagramSocket socket = new DatagramSocket(8091); // 포트 번호 12345로 소켓 생성
        byte[] buffer = new byte[1024]; // 버퍼 생성
        
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length); // 패킷 생성
            socket.receive(packet); // 소켓에서 패킷을 수신
            String message = new String(packet.getData(), 0, packet.getLength()); // 패킷의 데이터를 문자열로 변환
            System.out.println("Received packet: " + message); // 패킷 출력
        }
        
    }
    
}
