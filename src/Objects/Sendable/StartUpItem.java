package Objects.Sendable;

/**
 * A class that initialises information about the robot when it starts up, after the location information has been input
 * @author rkelly
 *
 */
public class StartUpItem implements SendableObject {
	
	private String name;
	private int x;
	private int y;
	private char direction; //N, E, S, W - if this has been changed to not be a char, I apologise
	
	public StartUpItem(String name, int x, int y, char direction) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public char getDirection() {
		return this.direction;// TODO Auto-generated method stub
	}
	
	public String toString() {
		return "StartUpItem [name=" + name + ", x=" + x + ", y=" + y + ", direction=" + direction + "]";
	}
	
	@Override
	public String parameters() {
		return ("StartUpItem," + name + "," + x + "," + y + "," + direction);
	}
}
