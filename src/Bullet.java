
import java.awt.Image;
import java.util.Random;

public class Bullet extends ObjectsAndCoordinate{
	
	int damage, direction, screenHeight;
	Random rand = new Random();
	int BulletSpeed = rand.nextInt(5)+10;
	
	
	public Bullet(int x, int y, int screenHeight, int damage, int direction, int w, int h, Image image){
		super(x, y, w, h, image);
		this.screenHeight = screenHeight;
		this.damage = damage;
		this.direction = direction;
	}
	
	// Move
	public void move(){
		if(direction == 1){
			setY(getY()+BulletSpeed);
		}
		else{
			setY(getY()-BulletSpeed);
		}
	}

	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDirection(){
		return direction;
	}

	public void setDirection(int direction){
		this.direction = direction;
	}
}
