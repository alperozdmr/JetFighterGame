
import java.awt.Image;

public class Plane extends ObjectsAndCoordinate {
	
	boolean alive;
	int health, speed, screenWidth, rocketNumber, score;

	public Plane(int x, int y, int w, int h, Image image) {
		super(x, y, w, h, image);
		alive = true;
		health = 150;
		speed = 10;
		rocketNumber = 250;
		score = 0;
		
	}
	public boolean isAlive(){
		return alive;
	}

	public void setAlive(boolean alive){
		this.alive = alive;
	}

	public int getHealth(){
		return health;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public void setScreenWidth(int w){
		screenWidth = w;
	}

	public void decreaseHealth(int x){
		health = health - x;
		if(health<=0) {
			alive = false;
		}
	}

	public void increaseX(){
		setX(getX()+1);
	}

	public void decreaseX(){
		setX(getX()-1);
	}

	public void increaseY(){
		setX(getX()+1);
	}

	public void decreaseY(){
		setX(getX()-1);
	}

	public void moveLeft(){
		for(int i=0; i<=speed; i++) {
			if(getX()>0) {
				decreaseX();				
			}
		}
	}

	public void moveRight(){
		for(int i=0; i<=speed; i++) {
			if(getX()<screenWidth-getWidth()) {
				increaseX();
			}
		}
	}


	public void decreaseRocket(){
		rocketNumber--;
	}


	public int getRocketNumber(){
		return rocketNumber;
	}

	public void setRocketNumber(int rocketNumber){
		this.rocketNumber = rocketNumber;
	}

	public int getScore(){
		return score;
	}

	public void setScore(int score){
		this.score = score;
	}

	public void increaseScore(int userscor){
		score +=userscor;
	}

}
