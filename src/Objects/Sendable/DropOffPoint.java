package Objects.Sendable;

/**
 * @author rkelly
 * A class that imitates a standard point so that it can be sent through the network
 */
public class DropOffPoint implements SendableObject {
	private int x;
	private int y;
	
	public DropOffPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "DropOffPoint [x=" + x + ", y=" + y + "]";
	}
	
	public String parameters() {
		return ("DropoffPoint," + x + y);
	}
}