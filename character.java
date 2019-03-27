import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Timer;

public class character {
	private int locationx, locationy;
	private int speedx, speedy;
	private int levelHeight;
	private int health;
	private boolean KnockedOut;
	protected int width = 30;
	protected int height = 30;
	protected int healthWidth = 20;
	protected int healthHeight = 20;
	static int g = 10;
	private Font font = new Font("Character",Font.BOLD,20);
	private int powerupblinking;
	
	//power up abilities.
	protected boolean INVINCIBLE;
	protected boolean SUPERJUMP;
	
	//constructor for main character.
	public character(int locationx, int locationy){
		
		powerupblinking = 0;
		
		this.locationx = locationx;
		this.locationy = locationy;
		this.speedy = 0;
		this.speedx = 0;
		this.health = 3;
		this.KnockedOut = false;
		
		this.INVINCIBLE = false;
		this.SUPERJUMP = false;
	}
	
	//getter and setter for x coordinate.
	public int getLocationx() {
		return locationx;
	}
	public void setLocationx(int locationx) {
		this.locationx = locationx;
	}
	//getter and setter for y coordinate.
	public int getLocationy() {
		return locationy;
	}
	public void setLocationy(int locationy) {
		this.locationy = locationy;
	}
	//getter and setter for player's health.
	public int getHealth(){
		return health;
	}
	public void setHealth(int health){
		this.health = health;
	}
	//getter and setter for vertical speed of main character.
	public int getSpeedy() {
		return speedy;
	}
	public void setSpeedy(int speedy) {
		this.speedy = speedy;
	}
	//getter and setter for horizontal movement speed.
	public int getSpeedx() {
		return speedx;
	}
	public void setSpeedx(int speedx) {
		this.speedx = speedx;
	}
	//getter and setter for if the main character is knocked out.
	public boolean isKnockedOut() {
		return KnockedOut;
	}
	public void setKnockedOut(boolean knockedOut) {
		KnockedOut = knockedOut;
	}
	//level height will give back the height of the current level the the character is about to land on.
	public int getLevelHeight() {
		return levelHeight;
	}
	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}

	//draw main character
	public void draw(Graphics g){
		//draw main character
		if(this.health == 3 && this.KnockedOut == false){
			g.setColor(Color.WHITE);
		}
		else if(this.health == 2 && this.KnockedOut == false){
			g.setColor(Color.YELLOW);
		}
		else{
			g.setColor(Color.RED);
		}
		
		if(INVINCIBLE||SUPERJUMP){
			powerupblinking++;
			if(powerupblinking%2 == 1){
				g.setColor(new Color(0,0,0,0));
			}
		}
		
		g.fillOval(locationx-width/2, locationy-height/2, width, height);
		
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("Health:", 440, 26);
		
		//draw health bar.
		for(int i = 0; i < health; i++){
			g.setColor(Color.RED);
			g.fillOval(530 + 35*i, 10, healthWidth, healthHeight);
		}
	}
	
	//jumping method.
	public void jump(){
		if(levelHeight < 600){
			//can only jump when on ground.
			if((locationy+height/2) >= levelHeight){
				speedy = -50;
			}
		}
		
	}
	
	
	//update main character.
	public void update(){
		//when super jump ability is used, reduce gravity to half.
		if(SUPERJUMP){
			g = g/2;
		}
		
		locationx += speedx;
		speedy = speedy+g;
		locationy += speedy;
		if((this.locationy+this.height/2) >= this.levelHeight && this.levelHeight <600){
			//set y coordinate to the height of the level.
			this.locationy = this.levelHeight-this.height/2;
			speedy = 0;
		}
		
		//when player get pushed out of the frame set knocked out to true.
		if(this.locationx + this.width/2 <= 0){
			health = 0;
			//System.out.println(KnockedOut);
		}
				
		//when player fall in bottomless pit set knocked out to true.
		if(this.levelHeight > 600 && this.locationy-this.height/2 >= this.levelHeight){
			health = 0;
		}
		
		//when player's health is zero, knocked out.
		if(this.health == 0){
			KnockedOut = true;
		}
		
		g = 10;
	}

}
