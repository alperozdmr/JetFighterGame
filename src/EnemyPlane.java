
import java.awt.Image;



public class EnemyPlane extends ObjectsAndCoordinate{
	
	boolean alive;
	int health;
	int screenWidth;
	int moveState;
	int moveCount;
	int ammo;

	
	public EnemyPlane(int x, int y, int w, int h, Image image, int screenWidth,int health,int ammo){
		super(x, y, w, h, image);
		alive = true;
		this.health=health;
		this.screenWidth = screenWidth;
		this.ammo=ammo;
	}

	
	public void decreaseHealth(int x){
		health = health - x;
		if(health<=0) {
			alive = false;
		}
	}

	// Move enemy plane left
	public void moveLeft(){
		if(getX()>5 ){
			setX(getX()-5);
			
		}
		else{
			moveState=1;
		}
	}

	// Move enemy plane right
	public void moveRight(){
		if(screenWidth-getWidth()>getX()+5 ){
			setX(getX()+5);
			
		}
		else{
			moveState=0;
		}
	}

	// Move enemy plane
	public void moveLR(){
		if(moveState==1){
			moveRight();
		}
		else{
			moveLeft();
		}
	}

	// Move enemy plane down
	public void moveDown(){
		setY(getY()+2);
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
	
	public int getMoveState(){
		return moveState;
	}

	public void setMoveState(int moveState){
		this.moveState = moveState;
	}

	public int getMoveCount(){
		return moveCount;
	}

	public void setMoveCount(int moveCount){
		this.moveCount = moveCount;
	}

	public int getAmmo(){
		return ammo;
	}

	public void setAmmo(int ammo){
		this.ammo = ammo;
	}
	
	public void decreaseAmmo(){
		ammo--;		
	}
}