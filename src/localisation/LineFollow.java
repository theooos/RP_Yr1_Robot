package localisation;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

/**
 * The default behaviour for following a line
 */
public class LineFollow implements Behavior {
	
	private LightSensor left;
	private LightSensor right;
	private int leftValue;
	private int rightValue;
	private int threshold;
	private boolean suppress=false;
	private DifferentialPilot pilot;

	
	public LineFollow(DifferentialPilot pilot, LightSensor left,LightSensor right, double speed, int threshold){
		this.pilot = pilot;
		this.left=left;
		this.right=right;
		this.threshold = threshold;
		pilot.setTravelSpeed(speed);
	}

	@Override
	public boolean takeControl() {
		return true;
	}
	

	@Override
	public void action() {
		
		pilot.forward();
		generateLightValues();
		
		while((leftValue > threshold || rightValue > threshold) && !suppress){
			float diff = leftValue - rightValue;			
			pilot.steer(-diff);
			generateLightValues();
		}
		
		pilot.stop();
		suppress = false;
		Delay.msDelay(100);
		
		//LCD.drawString("I found a junction", 0, 6);
	}

	@Override
	public void suppress() {
		suppress = true;

	}
	
	/**
	 * Generates calibrated light values
	 * On a black line = 45
	 * Not on a black line = 35
	 */
	
	private void generateLightValues(){
		leftValue = left.getLightValue();
		rightValue = right.getLightValue();
		//System.out.println("L: " + leftValue + " R: " + rightValue);
	}
}