package RobotMotionControl;
import Objects.Sendable.MoveReport;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

public class ObstacleDetection implements Behavior {
	
	private OpticalDistanceSensor ranger;
	private WheeledRobotSystem system;
	private boolean suppress=false;
    private DifferentialPilot pilot;

	
	public ObstacleDetection(WheeledRobotConfiguration config,double speed,OpticalDistanceSensor sensor){
		ranger=sensor;
		system = new WheeledRobotSystem(config);
		pilot=system.getPilot();
		pilot.setTravelSpeed(speed);
	}
	

	@Override
	public boolean takeControl() {
		if(ranger.getRange()<=12)
			return true;
		return false;
	}
	
	private MoveReport sendReport(boolean hasMoved){
		 LCD.drawString(hasMoved+"", 0, 0);
		 return new MoveReport(hasMoved);
	}
	
	@Override
	public void action() {
		pilot.backward();
		sendReport(false);
		while(!suppress){
			Delay.msDelay(50);
		}
		suppress=false;
	}
	
	@Override
	public void suppress() {
		suppress=true;

	}

}
