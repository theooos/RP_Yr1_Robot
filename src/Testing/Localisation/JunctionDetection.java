package Testing.Localisation;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.mapping.MapUtils;
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
	private int NORTH = 0;
	private int WEST = 1;
	private int SOUTH = 2;
	private int EAST = 3;
	private float robotDirection = NORTH;
	private float NORTH_DISTANCE;
	private float SOUTH_DISTANCE;
	private float WEST_DISTANCE;
	private float EAST_DISTANCE;
	private float DISTANCE;
	private boolean firstTime=false;
	private float heading;
	private int testCount = 0;
	private ProbableLocations locs = new ProbableLocations(MapUtils.createRealWarehouse());
	
	
	private OpticalDistanceSensor ranger;
	
	public JunctionDetection(WheeledRobotConfiguration config,LightSensor left,LightSensor right,double speed,OpticalDistanceSensor distanceSensor){
		this.system= new WheeledRobotSystem(config);
		this.left=left;
		this.right=right;
		pilot=system.getPilot();
		this.ranger=distanceSensor;
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
		//distanceSensorLCD.drawString(leftValue+" "+rightValue, 0, 0);
	}
	@Override
	public boolean takeControl(){
		
		generateLightValues();
		if(leftValue==threshold && rightValue==threshold){
			return true;
		}
		
		return false;
	}
	//Turns left
	private void moveLeft(){
		pilot.rotate(128);
	}
	//Turns right
	private void moveRight(){
		pilot.rotate(-128);			
	}
	//Continues going forward
	private void moveForward(){
		pilot.forward();
	}
	//Executes a move
	private void executeMove(int move){
		setDirections();
		//LCD.drawString(DISTANCE + " ", 0, testCount);
		locs.setLocations(heading, DISTANCE);
		if(move==Directions.LEFT){
			
			moveLeft();
			if(robotDirection==EAST)
				robotDirection=NORTH;
			else			
				robotDirection+=1;
			
		}
		else if(move==Directions.RIGHT){
			moveRight();
			if(robotDirection == WEST)
				robotDirection = NORTH;
			else
				robotDirection-=1;
		}
		else
			moveForward();
		
		testCount+=1;
		
	}

	private void setDirections(){
		if(robotDirection==NORTH){
			heading = 90;
			NORTH_DISTANCE=ranger.getRange();
			DISTANCE=ranger.getRange();
		}
		else if(robotDirection==EAST){
			heading = 0;
			EAST_DISTANCE=ranger.getRange();
			DISTANCE=ranger.getRange();
		}
		else if(robotDirection==SOUTH){
			heading = -90;
			SOUTH_DISTANCE=ranger.getRange();
			DISTANCE=ranger.getRange();
		}
		else{
			heading = 180;
			WEST_DISTANCE=ranger.getRange();
			DISTANCE=ranger.getRange();
		}
	}
	public void action() {
		pilot.travel(0.1f);
		if(!firstTime || ranger.getRange()<11){
			for(int i=0;i<4;i++){
				
				LCD.drawString("" + robotDirection+ " "+ locs.size(), 0, i);
				executeMove(Directions.LEFT);
				
			//	LCD.drawString(" " + robotDirection, 0, 0);
				//for(int j = 0; j < locs.size(); j++){
				//	LCD.drawString("H: " + robotDirection + " : " + locs.getPoints(j).getX() + " " + locs.getPoints(j).getY(), 0, 0);
				//}
				//LCD.drawString("" + robotDirection+ " "+ locs.size(), 0, i);
				
			}
			//pilot.stop();
			firstTime = true;
		}
	}

	@Override
	public void suppress() {
		
	}
}

