package game.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	 
    private DatagramSocket socket;
    private List<Player> players = new ArrayList<Player>();
 
    public Server() throws SocketException, IOException {
    	socket = new DatagramSocket(4445);
    }
 
    public static void main(String[] args) throws SocketException, IOException{
    	new Server().start();
    }
    
    public void run() {
        System.out.println("Server is running...");
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received \"" + received + "\" from " + packet.getAddress() + " " + packet.getPort());

            if (received.equals("end")) {
            	players = this.removeFromList(players, packet);

            }
            if (received.equals("start")) {
                players.add(new Player(packet.getAddress(), packet.getPort()));
            }
            
            for(Player player : players){
                packet = new DatagramPacket(packet.getData(), packet.getLength(), player.getIp(), player.getPort());
                try {
            		System.out.println("Sending info to: " + player.getIp() + " " + player.getPort());
    				socket.send(packet);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
            }
        }
    }
    
    /*private byte[] generateId() {
        byte[] buf = new byte[256];
        Random random = new Random();
    	buf = ByteBuffer.allocate(4).putInt(random.nextInt(100)).array();
    	return buf;
    }*/
    
	public void sendData(byte[] data, InetAddress address, int port) {
    	System.out.println("Sending message...");
    	DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    private List<Player> removeFromList(List<Player> list, DatagramPacket packet){
    	list.removeIf(player -> player.getIp().equals(packet.getAddress()) && player.getPort() == packet.getPort());
    	return list;
    }
}