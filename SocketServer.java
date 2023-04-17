import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;

public class SocketServer {
    static ExecutorService exec = Executors.newFixedThreadPool(2);
    static String[] quotes =  new String[]
            {

                    "\"I always think that everything could be a trap, which is why I'm still alive.\"" + "\n" + " - Prince Humperdinck (The Princess Bride)",
                    "\"Are you cussing with me?\"" + "\n" + " - Mr. Fox (Fantastic Mr. Fox)",
                    "\"I will tell you the truth and its up to you to live with it.\"" + "\n" + " - William Goldman (The Princess Bride)",
                    "\"Am I being flirted with by a psychotic rat?\"" + "\n" + " - Mrs. Felicity Fox (Fantastic Mr. Fox)",
                    "\"Life is pain, Highness. Anyone who says differently is selling something." + "\n" + " - Westley (The Princess Bride)",
                    "\"I don't even exercise.\"" + "\n" + " - Fezzik (The Princess Bride)",
                    "\"Tails don't grow back. I'm gonna be tail-less for the rest of my life.\"" + "\n" + " - Mr. Fox (Fantastic Mr. Fox"
            };
    static Random random = new Random();
    static int port = 17;

    public static void handleRequestTCP(Socket socket) {
        int index = random.nextInt(quotes.length);
        try {
            byte[] dataBytes = quotes[index].getBytes("UTF-8");

            OutputStream os = socket.getOutputStream();
            os.write(dataBytes);

            System.out.println("Message sent!");
            os.close();
            socket.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void tcp() {
        try (ServerSocket server = new ServerSocket(port)){
            Socket socket = null;

            System.out.println("Server is listening on port " + port);

            while((socket = server.accept()) != null) {
                System.out.println("Connection established. Sending the message...");
                final Socket threadSocket = socket;
                handleRequestTCP(threadSocket);
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void udp() {
        try {
            while(true) {
                int index = random.nextInt(quotes.length);

                String quote = quotes[index];
                byte[] buffer = quote.getBytes();

                DatagramPacket request = new DatagramPacket(new byte[1], 1);
                DatagramSocket datagramSocket = new DatagramSocket(port, request.getAddress());


                datagramSocket.receive(request);
                System.out.println("Received a datagram.");

                DatagramPacket response = new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());
                System.out.println("Sending Response...");
                datagramSocket.send(response);
                System.out.println("sent!");

                datagramSocket.close();
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String... args) {

        exec.submit(() -> tcp());
        exec.submit(() -> udp());

    }
}