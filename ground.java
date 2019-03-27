import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class ground {
	private List<level> ground;
	protected level level;
	private final int initialBound = 25;//initial number of level need to be created.
	private int canvasHeight;
	private character c;
	protected enemy enemy;
	protected powerup powerup;
	private int sum;//sum of previous level's width.
	
	//constructor.
	public ground(int canvasHeight, character c){
		this.canvasHeight = canvasHeight;
		this.c = c;
		
		ground = new ArrayList<>();
		createLevel(initialBound);
		
		enemy = new enemy(ground.get(ground.size()-3),//level in front of enemy;
						  ground.get(ground.size()-2),//level enemy is on;
						  ground.get(ground.size()-1));//level behind enemy;
		powerup = new powerup(ground.get(ground.size()-10));
		
	}
	
	//create new level
	public void createLevel(int bound){
		//System.out.println(sum);
		for(int i = 0;i<bound;i++){
			level = new level();
			//when it is first construct, create a plain field.
			if(bound > 10){
				level.setHeight(100);
			}
			level.setLocationy(canvasHeight - level.getHeight());
			level.setLocationx(sum);//every level's x coordinates is the sum of previous ones' width.
			sum = sum + level.getWidth();
			//System.out.println(sum);
			
			ground.add(level);
		}
	}
	
	//draw method.
	public void draw(Graphics g){
		for(int i = 0;i<ground.size();i++){
			level = ground.get(i);
			level.draw(g);
		}
		
		enemy.draw(g);
		powerup.draw(g);
	}
	//check to see if the first level is out of sight.
	public void check(){
		level = ground.get(0);
		
		sum = sum + level.getScrollSpeed();
		
		if(level.getLocationx() + level.getWidth() <= 0){
			
			ground.remove(0);
			
			createLevel(1);
		}
	}
	
	
	//update method to move the levels.
	public void update(){
		for(int i = 0; i<ground.size(); i++){
			level = ground.get(i);
			
			level.update();
			//after update the level, check to see if one of the level is beneath the character.
			level.check(c);
		}
		//let enemy move with level.
		enemy.update();
		//when enemy is out of the frame, change its states to a new location.
		if(enemy.getLocationx()+enemy.width<=0){
			enemy.setLevelPre(ground.get(ground.size()-3));
			enemy.setLevelOn(ground.get(ground.size()-2));
			enemy.setLevelPost(ground.get(ground.size()-1));
			enemy.createEnemy();
			enemy.assignSpeed();
			enemy.setEncounter(false);
		}
		//power up update
		powerup.update();
		powerup.powerOn(c);
		//if player missed power up.
		if(powerup.getLocationx()+powerup.width<=0 && powerup.isUsed() == false){
			powerup.setTime(0);
		}
		//when power up is out of the frame, change its states to a new location.
		if(powerup.getLocationx()+powerup.width<=0 && powerup.getTime() == 0 && powerup.elapsedtime%300 == 0){
			powerup.setLevelOn(ground.get(ground.size()-10));
			powerup.createPowerup();
			powerup.setUsed(false);
			powerup.setPowerType(powerup.randomType());
			powerup.setName(String.valueOf(powerup.getPowerType()));
			powerup.setTime(100);
		}
		
		System.out.println(ground.get(10).speed);
		System.out.println(c.INVINCIBLE+" "+c.SUPERJUMP+" "+powerup.isUsed()+" "+powerup.getTime());
		
	}
}
