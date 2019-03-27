import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//this class sets the background of the game.
//at the beginning of the game, this class will randomly choose day or night.
//the sun or moon at the top will be moving at a slow speed.
//when the sun reaches the other end of the frame, it will change from day to night or
//night to day.
//
public class background {
	private int sunLocationX;
	private int sunLocationY;
	private int canvasWidth;
	private int canvasHeight;
	private Color backgroundColor;
	private Color sunColor;
	
	public enum state{
		Sun,
		Moon;
	}
	
	//randomly generate day or night.
	private List<state> stateValue = Collections.unmodifiableList(Arrays.asList(state.values()));
	static final Random RANDOM = new Random();
	
	private state State;
	
	private Color DAY;
	private Color NIGHT;
	private Color SUN;
	private Color MOON;
	
	
	private final int sunSize = 100;
	private final int initialy = 50;
	private final int speed = -1;
	
	
	private int n;//number of level currently.
	private Font font1 = new Font("Bold",Font.ITALIC,50);
	Color fontColor1 = Color.GRAY;
	private Font font2 = new Font("Bold",Font.ITALIC,35);
	protected Color fontColor2 = Color.WHITE;
	
	public background(int canvasWidth, int canvasHeight, int n){
		this.n = n;
		this.sunLocationX = canvasWidth-sunSize;
		this.sunLocationY = initialy;
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.State = stateValue.get(RANDOM.nextInt(stateValue.size()));
		
		switch(n%2){
		case 1: DAY = Color.CYAN;
				NIGHT = Color.BLUE;
				SUN = Color.ORANGE;
				MOON = Color.YELLOW;
				break;
		case 0: DAY = Color.ORANGE;
				NIGHT = Color.GRAY;
				SUN = Color.GREEN;
				MOON = Color.BLACK;
				break;
		}
		
	}
	
	//draw method to draw the background.
	public void draw(Graphics g){
		switch(State){
		case Sun:
					this.backgroundColor = DAY;
					this.sunColor = SUN;
				break;
		case Moon:
					this.backgroundColor = NIGHT;
					this.sunColor = MOON;
				break;
		}
		
		g.setColor(this.backgroundColor);
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		g.setColor(this.sunColor);
		g.fillOval(this.sunLocationX, this.sunLocationY, this.sunSize, this.sunSize);
		
		
		
		drawCenteredString(g);
	}
	
	//draw starting String.
	public void drawCenteredString(Graphics g){
		g.setFont(font1);
		g.setColor(fontColor1);
		g.drawString("Level"+" "+ n, 250, 230);
		
		g.setColor(fontColor2);
		g.setFont(font2);
		g.drawString("Press enter to start", 170, 274);
	}
	
	public void update(){
		this.sunLocationX = this.sunLocationX + speed;
		
		if(this.sunLocationX+this.sunSize < 0 && State == state.Sun){
			this.sunLocationX = canvasWidth;
			this.sunColor = MOON;
			this.backgroundColor = NIGHT;
			this.State = state.Moon;
		}
		else if(this.sunLocationX+this.sunSize < 0 && State == state.Moon){
			this.sunLocationX = canvasWidth;
			this.sunColor = SUN;
			this.backgroundColor = DAY;
			this.State = state.Sun;
		}
	}
}
