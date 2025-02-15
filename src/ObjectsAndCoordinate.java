
import java.awt.Image;

public class ObjectsAndCoordinate {
	
	private int x; 
	private	int y; 
	private int width;			// Width of the object
	private int height;			// Height of the object
	private Image sourceImage;
	
	public ObjectsAndCoordinate(int x, int y, int w, int h, Image image) {
		this.x = x;
		this.y = y;
		width= w;
		height = h;
		sourceImage = image;
	}
	
	////// setter and getter ///////
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Image getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(Image sourceImage) {
		this.sourceImage = sourceImage;
	}

}
