import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class Application extends JFrame implements KeyListener,MouseListener {
	private canvas c;
	private Timer timer;
	private Boolean pause;
	private final int TimerDelay = 100;
	static int number = 1;
	
	public Application(int number){
		pause = true;
		
		timer = new Timer(TimerDelay,new Timerhandler());
		c = new canvas(timer,number);
		c.addKeyListener(this);
		c.addMouseListener(this);
		
		setTitle("是男人就坚持100秒！");
		setLayout(new BorderLayout());
		add(c, BorderLayout.CENTER);
		setSize(640,640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		
	}
	
	//create animation timer.
	protected class Timerhandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			c.update();
			c.repaint();
			
			
		}
		
	}
	
	//create key listener method.
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
			
		if(e.getKeyCode() == KeyEvent.VK_SPACE && timer.isRunning()){
			c.c.jump();
		}
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE && pause == false){
			timer.stop();
			c.gametime.previousTime = c.gametime.previousTime+c.gametime.elapsedTime;
			pause = true;
			//System.out.println(c.gametime.previousTime);
		}
		else if(e.getKeyCode() == KeyEvent.VK_ENTER && pause == true){
			timer.start();
			pause = false;
			c.gametime.gamestart();
			//System.out.println(c.gametime.previousTime);
			c.background.fontColor1 = new Color(0,0,0,0);
			c.background.fontColor2 = new Color(0,0,0,0);
		}
		else if(e.getKeyCode() == KeyEvent.VK_ENTER && c.c.isKnockedOut()){
			number++;
			level.speed = level.speed -5;
			Application a = new Application(number);
			a.setVisible(true);
			timer.start();
			System.out.println(number);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	//mouse listener.
	@Override
	public void mouseClicked(MouseEvent e) {
		c.c.jump();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	public static void main(String[] args){
		new Application(1).setVisible(true);
	}
	
}
