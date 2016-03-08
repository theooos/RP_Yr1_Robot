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
	private LightSensor left;
	private LightSensor right;
	private int leftValue;
	private int rightValue;
	private DifferentialPilot pilot;
	private final int threshold = 35;
	
	public ObstacleDetection(WheeledRobotConfiguration config,LightSensor left,LightSensor right,double speed,OpticalDistanceSensor sensor){
		ranger=sensor;
		system = new WheeledRobotSystem(config);
		this.left=left;
		this.right=right;
		pilot=system.getPilot();
		pilot.setTravelSpeed(speed);
	}
	
	private void generateLightValues(){
		leftValue=left.getLightValue();
		rightValue=(int)(right.getLightValue()*0.8);
		if(leftValue>40)
			leftValue=45;
		else
			leftValue=35;
		if(rightValue>40)
			rightValue=45;
		else
			rightValue=35;

	}
	@Override
	public boolean takeControl() {
		if(ranger.getRange()<=12)
			return true;
		return false;
	}
	
	private MoveReport sendReport(boolean hasMoved){
		 return new MoveReport(hasMoved);
	}
	
	@Override
	public void action() {

			while((leftValue!=threshold && rightValue!=threshold) && !suppress){
				pilot.backward();
				Delay.msDelay(25);
				//LCD.drawString(suppress+"", 0, 0);
			}
			pilot.stop();
			sendReport(false);
			suppress=false;
	}
	
	@Override
	public void suppress() {
		suppress=true;

	}

}
