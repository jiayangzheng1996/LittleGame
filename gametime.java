import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Scanner;

public class gametime {
	long starttime;
	long endtime;
	double elapsedTime;
	double previousTime;
	String string;
	private int runtime;
	private double sum;
	
	private Font font = new Font("Character",Font.BOLD,20);
	
	public gametime(){
		previousTime = 0;
		runtime = 0;
	}
	
	public void gamestart(){
		starttime = System.currentTimeMillis();
		runtime++;
	}
	
	//return the elapsed time.
	public String elapsedTime(){
		endtime = System.currentTimeMillis();
		//rounding time.
		elapsedTime = Math.floor((endtime - starttime)/100.0)/10.0;
		previousTime = Math.floor(previousTime*10)/10.0;
		
		sum = previousTime + elapsedTime;
		sum = Math.round(sum*10);
		sum = sum/10.0;
		
		string = String.valueOf(sum);
		return string;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.setFont(font);
		if(runtime > 0){
			g.drawString(elapsedTime(), 50, 26);
		}
		else
			g.drawString("0.0", 50, 26);
	}
}
