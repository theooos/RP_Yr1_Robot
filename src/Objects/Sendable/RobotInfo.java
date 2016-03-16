package Objects.Sendable;

import java.awt.Point;

import Objects.Direction;

public class RobotInfo implements SendableObject {

	private String name;
	private Point location;
	private Direction direction;
	
	public RobotInfo(String name) {
		this.name = name;
	}
	
	public RobotInfo(String name, Point location, Direction direction) {
		this.name = name;
		this.location = location;
		this.direction = direction;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String parameters() {
		return "RobotInfo," + name + "," + (int)location.getX() + "," + (int)location.getY() + "," + direction;
	}
}