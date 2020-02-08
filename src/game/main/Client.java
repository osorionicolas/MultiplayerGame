package game.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Client extends Thread {
	
    private DatagramSocket socket;
    private InetAddress address;
    private Game game;
    
    public Client(Game game) {
    	this.game = game;
    	try {
			this.socket = new DatagramSocket();
	    	this.address = InetAddress.getByName("localhost");
	    	this.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
    
    public void run() {
    	while(true) {
    	    byte[] data = new byte[1024];
    		DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
    			socket.receive(packet);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            String received = new String(packet.getData(), 0, packet.getLength());
            if (received.equals("start")) {
	            System.out.println("Starting...");
            	this.game.createNew();
            }
            else {
	            int receivedInt =  ByteBuffer.wrap(packet.getData()).getInt();
	            System.out.println(receivedInt);
	        	this.game.update(receivedInt, 50);
            }
    	}
    }

	public void sendData(byte[] data) {
    	System.out.println("Sending message...");
    	DatagramPacket packet = new DatagramPacket(data, data.length, address, 4445);
        try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    public void close() throws SocketException, UnknownHostException {
        socket.close();
    }
}
