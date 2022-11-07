import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class GroupChat {
    public static volatile boolean finished=false;

    public static void main(String [] args) throws IOException {
        int port = 3900;
        InetAddress addressGroup = InetAddress.getByName("239.192.0.1");
        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.setTimeToLive(0);
        multicastSocket.joinGroup(addressGroup);

        Thread thread = new Thread(new ReadThread(multicastSocket,addressGroup,port));
        thread.start();

        Scanner scanner = new Scanner(System.in);

        while (true){
            String message = scanner.nextLine();
            if(message.equals("exit")){
                finished=true;
                multicastSocket.leaveGroup(addressGroup);
                multicastSocket.close();
                break;
            }

            byte[] buffer = message.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length,addressGroup,port);
            multicastSocket.send(datagramPacket);
        }
    }
}
