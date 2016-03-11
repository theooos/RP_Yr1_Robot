package Objects.Sendable;

public class RobotInfo implements SendableObject {

	private String name;
	
	public RobotInfo(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String parameters() {
		return "RobotInfo," + name;
	}
}