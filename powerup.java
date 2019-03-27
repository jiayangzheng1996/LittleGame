import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class powerup {
	public enum type{
		INVINCIBLE,
		SUPERJUMP;
	}
	private static final List<type> Type = Collections.unmodifiableList(Arrays.asList(type.values()));
	static final Random RANDOM = new Random();
	
	public type randomType(){
		return Type.get(RANDOM.nextInt(Type.size()));
	}
	
	private type powerType;
	private String name;
	private level levelOn;
	
	private boolean used;
	private int locationx;
	private int locationy;
	protected int width = 20, height = 20;
	private int levelWidth;
	private int levelx, levely;
	
	protected int elapsedtime = 0;
	
	private Font font = new Font("Powerup",Font.BOLD,15);
	
	private int time = 100;
	
	//getter and setter for the current level the powerup is on.
	public level getLevelOn() {
		return levelOn;
	}
	public void setLevelOn(level levelOn) {
		this.levelOn = levelOn;
	}
	//getter and setter for power type.
	public type getPowerType() {
		return powerType;
	}
	public void setPowerType(type powerType) {
		this.powerType = powerType;
	}
	//getter and setter for location x.
	public int getLocationx() {
		return locationx;
	}
	public void setLocationx(int locationx) {
		this.locationx = locationx;
	}
	//getter and setter for location y.
	public int getLocationy() {
		return locationy;
	}
	public void setLocationy(int locationy) {
		this.locationy = locationy;
	}
	//getter and setter for name.
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//getter and setter for used.
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	//getter and setter for time.
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	//constructor of power up.
	public powerup(level levelOn){
		this.levelOn = levelOn;
		this.used = false;
		
		createPowerup();
		
		this.powerType = randomType();
		name = String.valueOf(powerType);
		
	}
	
	//when the character touch power up.
	public void powerOn(character c){
		//only run when power up is used.
		if(used == true){
			if(powerType == type.INVINCIBLE && time > 0){
				//enemies cannot cause damage on the character.
				c.INVINCIBLE = true;
			}
			else if(powerType == type.SUPERJUMP && time > 0){
				//gravity reduce by half.
				c.SUPERJUMP = true;
			}
			else if(time == 0){
				c.INVINCIBLE = false;
				c.SUPERJUMP = false;
			}
		}
		
	}
	
	//when character collide with power up.
	public void collision(character c){
		int playerx = c.getLocationx();
		int playery = c.getLocationy();
		
		//when player is on the ground.
		//when the left edge of the character pass the right edge of the enemy
		//and right edge of the character did not pass the left edge of the enemy
		//at the same time the lower edge of the character pass the upper edge of the enemy.
		//and invincible ability is not activated.
		if(playerx+c.width/2 >= this.locationx 
		   && playerx-c.width/2 <= this.locationx+this.width
		   && playery+c.height/2 >= this.locationy
		   && playery-c.height/2 <= this.locationy+this.height){
			this.used = true;
		}
	}
	
	
		
	//set power up location.
	public void createPowerup(){
		levely = levelOn.getLocationy();
		levelx = levelOn.getLocationx();
		levelWidth = levelOn.getWidth();
		
		locationx = levelx+(levelWidth/2)-this.width/2;
		locationy = levely - this.height-50;
		if(levelOn.getHeight() == 0){
			locationy = 540;
		}
	}
	
	public void draw(Graphics g){
		if(this.used == false){
			g.setColor(Color.YELLOW);
		}
		else{
			g.setColor(new Color(0,0,0,0));
		}
		
		g.fillOval(locationx, locationy, width, height);
		
		//draw power up duration.
		if(used && time>0){
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString(name, 255, 20);
			g.drawRect(250, 30, 100, 20);
			g.setColor(Color.GREEN);
			g.fillRect(250, 30, time, 20);
		}
	}
	//update method
	public void update(){
		locationx = locationx + levelOn.getScrollSpeed();
		if(used && time > 0){
			time = time-1;
		}
		elapsedtime++;
		
	}
	
	
}
