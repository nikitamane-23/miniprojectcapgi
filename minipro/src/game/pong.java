package game;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class pong extends Applet implements Runnable, KeyListener {
	
	final int WIDTH=700, HEIGHT =500;
	Thread thread;
	HumanPole p1;
	AnotherPole p2;
	Ball b1;
	boolean gameStarted;
	Graphics gfx;
	Image img;	
	
	public void init(){	
		this.resize(WIDTH,HEIGHT);
		gameStarted=false;
		this.addKeyListener(this);
		p1=new HumanPole(1);
		b1=new Ball();
		p2=new AnotherPole(2,b1);
		img=createImage(WIDTH,HEIGHT);
		gfx=img.getGraphics();
		thread=new Thread(this);
		thread.start();
	}
	
public void paint(Graphics g) {
	gfx.setColor(Color.black);
	gfx.fillRect(0, 0, WIDTH, HEIGHT);
	if(b1.getX()<-10 || b1.getX()> 710) {
		gfx.setColor(Color.RED);
		gfx.drawString("Better Luck Next Time...!", 290,100);
		gfx.drawString("Game Over", 320, 120);
	}
	else {
		p1.draw(gfx);
		b1.draw(gfx);
		p2.draw(gfx);
	}
	if(!gameStarted){
		gfx.setColor(Color.YELLOW);
		gfx.drawString("Welcome to Pong Game Designed By Nikita Mane", 200,100);
		gfx.setColor(Color.BLUE);
		gfx.drawString("Press Enter To Strat Game", 250,120);		
	}
	g.drawImage(img,0,0,this);	
}

public void update(Graphics g) {
	paint(g);
}
public void run() {
	for(; ;) {
		if(gameStarted) {
		p1.move();
		p2.move();
		b1.move();
		b1.checkPoleCollision(p1, p2);
		}
		repaint();
		try {
			Thread.sleep(10);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}

public void keyPressed(KeyEvent e) {
	if(e.getKeyCode()==KeyEvent.VK_UP) {
		p1.setupAccel(true);
	}
	else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
		p1.setdownAccel(true);
	}else if(e.getKeyCode()==KeyEvent.VK_ENTER) {
		gameStarted=true;
	}	
}

public void keyReleased(KeyEvent e) {
	if(e.getKeyCode()==KeyEvent.VK_UP) {
		p1.setupAccel(false);
	}
	else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
		p1.setdownAccel(false);
	}
}

public void keyTyped(KeyEvent e) {	
}
}
