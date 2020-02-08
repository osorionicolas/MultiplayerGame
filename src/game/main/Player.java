package game.main;

import java.net.InetAddress;

public class Player {

	private int id;
	private InetAddress ip;
	private int port;
	
	public Player(InetAddress ip, int port) {
		this.setIp(ip);
		this.setPort(port);
	}
	
	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
