package Objects.Sendable;

import java.awt.Point;
import java.util.UUID;

public class RobotInfo implements SendableObject {

	private UUID name;
	private Point position;
	
	public RobotInfo(UUID name, Point position) {
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
		return (name + "," + (int)position.getX() + "," + (int)position.getY());
	}

}
