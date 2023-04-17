import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) {

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        String message = "";
        for (int i = 2; i < args.length; i++) {
            message += args[i] + " ";
        }
        message = message.trim();

        DatagramSocket udpSocket = null;
        System.out.println("UDP Client connected on port " + port + " to server: " + host);
        try {
            InetAddress address = InetAddress.getByName(host);

            udpSocket = new DatagramSocket();
            byte[] requestData = message.getBytes("UTF-8");
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, address, port);
            udpSocket.send(requestPacket);

            byte[] buffer = new byte[512];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(responsePacket);

            String replyContent = new String(buffer);
            System.out.println("Received: " + replyContent);
            udpSocket.close();

        } catch (IOException err) {
            System.err.println("Error communicating with server");
            err.printStackTrace();
        }
    }
}
