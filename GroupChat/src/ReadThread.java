import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ReadThread implements Runnable{

    private MulticastSocket socket;
    private InetAddress socketAddresses;
    private int port;
    private final static int MAX_LENGTH = 1000;

    public ReadThread(MulticastSocket multicastSocket, InetAddress socketAddresses, int port) {
        this.socket = multicastSocket;
        this.socketAddresses = socketAddresses;
        this.port = port;
    }

    @Override
    public void run() {
        while (GroupChat.finished){
            byte[] buffer = new byte[MAX_LENGTH];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length,socketAddresses,port);

            try{
                socket.receive(datagramPacket);
                String received = new String(buffer,0,datagramPacket.getLength(),"UTF-8");
                System.out.println(received);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
