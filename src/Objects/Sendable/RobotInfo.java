package Objects.Sendable;

import java.awt.Point;

import Objects.Direction;

public class RobotInfo implements SendableObject {

	private String name;
	private Point position;
	private Direction direction;

	//By default, all robots start facing 'north' (which is west IRL)
	public RobotInfo(String name, Point position) {
		this.name = name;
		this.position = position;
		direction = Direction.NORTH;
	}

	public RobotInfo(String name, Point position, Direction direction) {
		this.name = name;
		this.position = position;
		this.direction = direction;
	}
	
	public String getName() {
		return name;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position)
	{
		this.position = position;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "RobotInfo [name=" + name + ", position=" + position + ", direction=" + direction + "]";
	}

	/**
	 * Gets the parameters in csv format
	 * @return all parameters, seperated by commas
	 */
	public String parameters() {
		return ("RobotInfo," + name + "," + (int)position.getX() + "," + (int)position.getY() + "," + direction);
	}

}
