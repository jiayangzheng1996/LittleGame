import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class enemy {
	private int speedx,speedy;
	
	private List<Integer> speedxyValue = new ArrayList<>(Arrays.asList(10,-10,0));
	private final Random RANDOM = new Random();
	
	private int locationx;
	private int locationy;
	private boolean encounter;
	protected int width = 30;
	protected int height = 30;
	
	private level levelOn;
	private level levelPre; //level that was in front of enemy.
	private level levelPost; // level that was behind level of enemy.
	
	protected final int characterSpeed = -40;
	
	public enemy(level levelPre, level levelOn, level levelPost){
		this.levelPre = levelPre;
		this.levelOn = levelOn;
		this.levelPost = levelPost;
		encounter = false;
		assignSpeed();
		createEnemy();
	}
	
	//getter and setter for enemy speed horizontally.
	public int getSpeedx() {
		return speedx;
	}
	public void setSpeedx(int speedx) {
		this.speedx = speedx;
	}
	//getter and setter for enemy speed vertically.
	public int getSpeedy() {
		return speedy;
	}
	public void setSpeedy(int speedy) {
		this.speedy = speedy;
	}
	//getter and setter for enemy x coordinate.
	public int getLocationx() {
		return locationx;
	}
	public void setLocationx(int locationx) {
		this.locationx = locationx;
	}
	//getter and setter for enemy y coordinate.
	public int getLocationy() {
		return locationy;
	}
	public void setLocationy(int locationy) {
		this.locationy = locationy;
	}
	//getter and setter for enemy width.
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	//getter and setter for enemy height.
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	//getter and setter for the level the enemy is currently on.
	public level getLevelOn() {
		return levelOn;
	}
	public void setLevelOn(level levelOn) {
		this.levelOn = levelOn;
	}
	//getter and setter for the level in front of enemy.
	public level getLevelPre() {
		return levelPre;
	}
	public void setLevelPre(level levelPre) {
		this.levelPre = levelPre;
	}
	//getter and setter for the level behind enemy.
	public level getLevelPost() {
		return levelPost;
	}
	public void setLevelPost(level levelPost) {
		this.levelPost = levelPost;
	}
	//getter and setter for encounter.
	public boolean isEncounter() {
		return encounter;
	}
	public void setEncounter(boolean encounter) {
		this.encounter = encounter;
	}

	//when player collide with the enemy decrease health.
	public void damage(character c){
		int playerx = c.getLocationx();
		int playery = c.getLocationy();
		int health = c.getHealth();
		
		//when player is on the ground.
		//when the left edge of the character pass the right edge of the enemy
		//and right edge of the character did not pass the left edge of the enemy
		//at the same time the lower edge of the character pass the upper edge of the enemy.
		//and invincible ability is not activated.
		if(playerx+c.width/2 >= this.locationx 
		   && playerx-c.width/2 <= this.locationx+this.width
		   && playery+c.height/2 >= this.locationy
		   && playery-c.height/2 <= this.locationy+this.height
		   && c.INVINCIBLE == false){
			if(health>0 && encounter == false){
				health--;
				c.setHealth(health);
				
				//set encounter to true to prevent repeated reduce of health.
				encounter = true;
				
//				playerx = playerx+this.characterSpeed;
//				c.setLocationx(playerx);
				c.setSpeedy(characterSpeed);
			}
		}
		//when player is in the middle of a jump.
//		else if(playery+c.height/2 < c.getLevelHeight()
//				&& playerx+c.width/2 >= locationx+levelOn.getScrollSpeed()
//				&& playerx-c.width/2 <= locationx+this.width+levelOn.getScrollSpeed()
//				&& playery-c.height/2+c.getSpeedy() <= this.locationy+this.height
//				&& playery+c.height/2+c.getSpeedy() >= this.locationy
//				&& c.INVINCIBLE == false){
//			if(health>0 && encounter == false){
//				health--;
//				c.setHealth(health);
//				
//				//set encounter to true to prevent repeated reduce of health.
//				encounter = true;
//				
//				c.setLocationx(c.getLocationx()+this.characterSpeed);
//				c.setSpeedy(characterSpeed);
//			}
//		}
	}
	
	//draw method.
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(locationx, locationy, width, height);
		//System.out.println(1);
	}
	
	//create enemy.
	public void createEnemy(){
		
		int levelLocationx,levelWidth;
		int levelLocationy;
		
		//check to see which one of the three adjacent level is the highest.
		if(levelPre.getHeight() >= levelOn.getHeight() 
				&& levelPost.getHeight() >= levelPost.getHeight()){
			levelOn = levelPre;
		}
		else if(levelOn.getHeight() >= levelPre.getHeight() 
				&& levelOn.getHeight() >= levelPost.getHeight()){
		}
		else{
			levelOn = levelPost;
		}
		
		levelLocationx = levelOn.getLocationx();
		levelWidth = levelOn.getWidth();
		levelLocationy = levelOn.getLocationy();
		
		//setting x coordinate for enemy.
		locationx = levelLocationx+(levelWidth/2)-this.width/2;
		
		//setting y coordinate for enemy
		if(levelOn.getLocationy() < 600){	
			locationy = levelLocationy-this.height;
			
		}
		else{
			locationy = levelLocationy-120-this.height;
			
		}
	}
	
	//enemy update method, enemy should move with the level it is currently on.
	public void update(){
		//when slow time ability in use, reduce half the speed.
		if(levelPre.getLocationx() >= locationx + levelOn.getScrollSpeed()
				|| (levelPost.getLocationx() + levelPost.getWidth()) 
				<= locationx+width-levelPost.getScrollSpeed()){
			speedx = -speedx;
		}
		locationx = locationx + levelOn.getScrollSpeed() + speedx;
		
		if(levelOn.getLocationy()-height < locationy
				|| levelOn.getLocationy()-300 >= locationy){
			speedy = -speedy;
		}
		locationy = locationy + speedy;
	}
	
	//randomly assign speed to enemy.
	public void assignSpeed(){
		speedx = 0;
		speedy = 0;
		
		if(RANDOM.nextBoolean()){
			speedx = speedxyValue.get(RANDOM.nextInt(speedxyValue.size()));
		}
		else
			speedy = speedxyValue.get(RANDOM.nextInt(speedxyValue.size()));
		}
	}
	

