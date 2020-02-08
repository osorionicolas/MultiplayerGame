package game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener{

	private static final long serialVersionUID = 7609414257268433642L;

    public int x = 50;
    public int y = 50;
	private Circle circle;
	private Client client;
	
    public static void main(String[] args) throws SocketException, UnknownHostException {
    	JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.add(new Game());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Game() throws SocketException, UnknownHostException {
        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400, 400));
        client = new Client(this);
        client.sendData("start".getBytes());
    }
    
    public void update(int x, int y){
    	this.x = x;
    	this.y = y;
    	this.repaint();
    }
    
    public void createNew() {
    	Graphics g = this.getGraphics();
        circle = new Circle(this.x + 50, this.y + 50);
       	circle.draw(g);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        circle = new Circle(this.x, this.y);
       	circle.draw(g);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
	        case KeyEvent.VK_RIGHT:
	        	this.moveRight();
	        	break;
	        
	        case KeyEvent.VK_LEFT:
	        	this.moveLeft();
	            break;
	            
	        case KeyEvent.VK_DOWN:
	        	this.moveDown();
	            break;
	            
	        case KeyEvent.VK_UP:
	        	this.moveUp();
	            break;
	            
	        case KeyEvent.VK_ESCAPE:
	        	this.exit();
	            break;
        }
    }
    

    private void moveRight(){
    	client.sendData(ByteBuffer.allocate(4).putInt(x += 10).array());
    }

    private void moveLeft() {
    	client.sendData(ByteBuffer.allocate(4).putInt(x -= 10).array());
    }

    private void moveDown() {
    	client.sendData(ByteBuffer.allocate(4).putInt(y += 10).array());
    }

    private void moveUp() {
    	client.sendData(ByteBuffer.allocate(4).putInt(y -= 10).array());
    }
    
    private void exit() {
    	client.sendData("end".getBytes());
    	try {
			client.close();
			System.exit(0);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }
    
}