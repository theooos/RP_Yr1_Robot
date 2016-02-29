package Objects;

import java.awt.Point;
import java.util.UUID;

public class RobotInfo {

	private UUID name;
	private Point position;
	
	public RobotInfo() {
		this.name = name;
		this.position = position;
	}
	
	public UUID getName() {
		return name;
	}
	
	public Point getPosition() {
		return position;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "RobotInfo [name=" + name + ", position=" + position + "]";
	}

	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return (name + "," + position.getX() + "," + position.getY());
	}

}
