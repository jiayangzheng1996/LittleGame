import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class level {
	private int width;
	private int height;
	private int scrollSpeed;
	private int locationx;
	private int locationy;
	static Color BROWN = new Color(156,93,82);
	static int speed = -25;
	
	static final Random RANDOM = new Random();
	
	protected List<Integer> widthValues =
			new ArrayList<>(Arrays.asList(-speed*2,-speed*2,-speed*2,-speed*2,-speed*2,-speed*3));
	protected List<Integer> heightValues = 
			new ArrayList<>(Arrays.asList(0,75,75,100,100,100,100,100,100,125));
	
	public level(){
		this.width = widthValues.get(RANDOM.nextInt(widthValues.size()));
		this.height = heightValues.get(RANDOM.nextInt(heightValues.size()));
		this.scrollSpeed = speed;
	}
	
	//getter and setter for width of the level
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	//getter and setter for height of the level.
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	//getter and setter for the speed the level is moving
	public int getScrollSpeed() {
		return scrollSpeed;
	}
	public void setScrollSpeed(int scrollSpeed) {
		this.scrollSpeed = scrollSpeed;
	}
	//getter and setter for x coordinate of the level.
	public int getLocationx() {
		return locationx;
	}
	public void setLocationx(int locationx) {
		this.locationx = locationx;
	}
	//getter and setter for y coordinate of the level.
	public int getLocationy() {
		return locationy;
	}
	public void setLocationy(int locationy) {
		this.locationy = locationy;
	}


	//draw method.
	public void draw(Graphics g){
		
		g.setColor(BROWN);
		g.fillRect(locationx, locationy, width, height);
		//draw green top that can be landed.
		g.setColor(Color.GREEN);
		g.fillRect(locationx, locationy, width, 20);
		
	}
	
	//determine which level is beneath the character
	public void check(character c){
		//check to see if the character hits the wall.
		Collision(c);
		
		//set character's y coordinate to height of the level.
		if(this.locationx < c.getLocationx()-5){

			c.setLevelHeight(this.locationy);
		}
		
	}
	
	//only detect collision when the player hit the level not landing on it.
	public void Collision(character c){
		if((c.getLocationy()+c.height/2) > this.locationy
				&& c.getLocationx() < (this.locationx + this.width)
				&& (c.getLocationx()+c.width/2) > this.locationx){
			c.setLocationx(this.locationx - c.width/2);
		}
		
	}
	
	
	
	@Override
	public String toString() {
		return "level [width=" + width + ", height=" + height + ", scrollSpeed=" + scrollSpeed + ", locationx="
				+ locationx + ", locationy=" + locationy + "]";
	}

	//update method on the level.
	public void update(){
		locationx += scrollSpeed;
		scrollSpeed = speed;
		//System.out.println(toString());
	}
	
}
