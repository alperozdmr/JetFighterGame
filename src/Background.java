
import java.awt.Image;


public class Background extends ObjectsAndCoordinate {
	/// DYNAMİC BACKGROUND////
	
	int speed;
	public Background(int x, int y, int w, int h, Image image){
		super(x, y, w, h, image);
		speed = 5;
	}

	
	
	public void move(){
		setY(getY() + speed);
	}

	public void setSpeed(int s){
		speed = s;
	}
}
