import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

/**
 * Behaviour for detecting junctions
 */
public class JunctionDetection implements Behavior {
	
	private WheeledRobotSystem system;
	private LightSensor left;
	private LightSensor right;
	private int leftValue;
	private int rightValue;
	private DifferentialPilot pilot;
	//Light value threshold
	private final int threshold = 35;
	//Moves
	private final int LEFT=0;
	private final int RIGHT=1;
	private final int FORWARD=2;
	//Test array 
	private int[] pattern = {FORWARD,RIGHT,LEFT,LEFT,RIGHT};
	private int i=0;
	

	public JunctionDetection(WheeledRobotConfiguration config,LightSensor left,LightSensor right,double speed){
		this.system= new WheeledRobotSystem(config);
		this.left=left;
		this.right=right;
		pilot=system.getPilot();
		pilot.setTravelSpeed(speed);
	}
	/**
	 * Generates calibrated values
	 * On a black line = 45
	 * Not on a black line = 35
	 */
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
		//LCD.drawString(leftValue+" "+rightValue, 0, 0);
	}
	@Override
	public boolean takeControl() {
		
		generateLightValues();
		if(leftValue==threshold && rightValue==threshold){
			return true;
		}
		
		return false;
	}
	//Turns left
	private void moveLeft(){
		pilot.rotate(120);
	}
	//Turns right
	private void moveRight(){
		pilot.rotate(-120);
	}
	//Continues going forward
	private void moveForward(){
		pilot.forward();
	}
	//Executes a move
	private void executeMove(int move){
		if(move==LEFT)
			moveLeft();
		else if(move==RIGHT)
			moveRight();
		else
			moveForward();
			
	}

	@Override
	/**	This is where the magic happens
	 * 	Will work on this one when Theo is ready with the networking
	 */
	public void action() {
		
		
		if(i<pattern.length)
		{
			pilot.travel(0.1f);
			
			executeMove(pattern[i]);
			i++;
		}
		else
			i=0;
	}

	@Override
	public void suppress() {
		
	}

}

