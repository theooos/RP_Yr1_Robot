package RobotMotionControl;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

/**
 * The default behaviour for following a line
 */
public class LineFollow implements Behavior {
	
	private WheeledRobotSystem system;
	private LightSensor left;
	private LightSensor right;
	private int leftValue;
	private int rightValue;
	private int error;
	private boolean suppress=false;
	//scale for the steering angle
	private final int scale = 6;
	private DifferentialPilot pilot;

	
	public LineFollow(WheeledRobotConfiguration config,LightSensor left,LightSensor right,double speed){
		this.system= new WheeledRobotSystem(config);
		this.left=left;
		this.right=right;
		pilot=system.getPilot();
		pilot.setTravelSpeed(speed);
	}

	@Override
	public boolean takeControl() {
		
		return true;
	}
	/**
	 * Generates calibrated light values
	 * On a black line = 45
	 * Not on a black line = 35
	 */
	private void generateLightValues(){
		leftValue=left.getLightValue();
		rightValue=(int)(right.getLightValue()*0.8);
		if(leftValue>39)
			leftValue=45;
		else
			leftValue=35;
		if(rightValue>39)
			rightValue=45;
		else
			rightValue=35;

	}

	@Override
	/**
	 * Uses an error (difference between light values) to determine if it should steer or not
	 */
	public void action() {
		LCD.drawString("LineFollow", 0, 2);
		pilot.forward();

		while(!suppress){
			
			generateLightValues();
		    error=leftValue-rightValue;
//			LCD.drawString(leftValue+" "+rightValue+" "+error, 0, 0);
			if(error==10||error==-10){
				pilot.steer((double)(-error * scale));
			}
			else
				pilot.steer(0);
			
			
			Delay.msDelay(25);
		}
		suppress=false;
	}

	@Override
	public void suppress() {
		suppress = true;

	}
}