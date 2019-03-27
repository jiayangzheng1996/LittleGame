import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.Timer;



public class canvas extends JComponent {
	protected character c;
	
	protected ground ground;
	protected background background;
	protected gametime gametime;
	private Timer timer;
	
	final int width = 640;
	final int height = 640;
	
	private Font font = new Font("Bold",Font.ITALIC,35);
	protected Color fontColor = Color.WHITE;
	
	
	public canvas(Timer timer, int number){
		this.timer = timer;
		c = new character(300,503);
		ground = new ground(618,c);
		background = new background(640,618,number);
		gametime = new gametime();
		
		
		this.setFocusable(true);
	}
	
	//paint component method.
	@Override
	public void paintComponent(Graphics g){

		background.draw(g);
		gametime.draw(g);
		ground.draw(g);
		c.draw(g);
		if(c.isKnockedOut()){
			draw(g);
			timer.stop();
		}
	}
	
	public void draw(Graphics g){
		g.setFont(font);
		g.setColor(fontColor);
		g.drawString("You Die!", 250, 230);
		g.drawString("You last "+gametime.elapsedTime()+" seconds!", 135, 274);
		g.drawString("Press enter to advance", 130, 318);
		g.drawString("to next level!", 210, 362);
	}
	
	//canvas will update every component.
	public void update(){
		background.update();
		ground.update();
		
		c.update();
		ground.check();
		ground.enemy.damage(c);
		ground.powerup.collision(c);
		
//		if(c.isKnockedOut()){
//			timer.stop();
//		}
	}
}
