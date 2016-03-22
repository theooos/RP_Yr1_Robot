package localisation;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.robotics.mapping.MapUtils;

/**
 * Behaviour for detecting junctions
 */
public class JunctionDetection implements Behavior {
	
	private LightSensor left;
	private LightSensor right;
	private int leftValue;
	private int rightValue;
	private DifferentialPilot pilot;
	//Light value threshold
	private int threshold;
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
	
	private ProbableLocations locs = new ProbableLocations(MapUtils.createRealWarehouse());
	private float cellSize = locs.getCellSize();
	
	private OpticalDistanceSensor ranger;
	
	public JunctionDetection(DifferentialPilot pilot, LightSensor left, LightSensor right, double speed, OpticalDistanceSensor distanceSensor, int threshold){
		this.left=left;
		this.right=right;
		this.pilot=pilot;
		this.ranger=distanceSensor;
		this.threshold = threshold;
		pilot.setTravelSpeed(speed);
	}
	/**
	 * Generates calibrated values
	 * On a black line = 45
	 * Not on a black line = 35			
	 */	
	
	@Override
	public boolean takeControl(){
		
		generateLightValues();
		if((leftValue < threshold) && (rightValue < threshold)){
			return true;
		}
		
		return false;
	}
	

	public void action() {
		pilot.travel(0.1f);
		//LCD.drawString(ranger.getRange() + " " + locs.getCellSize() , 0, 6);
		if(!firstTime || ranger.getRange()<locs.getCellSize()){
			
			for(int i=0;i<4;i++){
				
				//LCD.clear();
				//LCD.drawString(+ locs.size() + "      " + ranger.getRange(), 0, 7);
				checkEnd(locs.size());
				executeMove(Directions.LEFT);
				Delay.msDelay(100);
//				if(locs.size() < 7){
//					for(int i1 = 0; i1 < locs.size(); i1++){
//						//LCD.drawString(locs.getPoints(i1).getxCoord() + " " + locs.getPoints(i1).getyCoord(), 0, i1+1);
//					}
//				}
//				Delay.msDelay(3000);
				
			}
			//pilot.stop();
			
			firstTime = true;
		}
		
		checkEnd(locs.size());
		
		if(ranger.getRange()<locs.getCellSize()){
			//LCD.drawString("I am in with: " + ranger.getRange() , 0, 0);
			if(heading == 90){
				turnDecision(WEST_DISTANCE, EAST_DISTANCE);
					
			}else if(heading == 180){
				turnDecision(NORTH_DISTANCE, SOUTH_DISTANCE);
				
			}else if(heading == -90){
				turnDecision(EAST_DISTANCE, WEST_DISTANCE);
				
			}else if(heading == 0){
				turnDecision(SOUTH_DISTANCE, NORTH_DISTANCE);
			}
			 
		}else{
			executeMove(Directions.FORWARD);
			locs.updateLocations(heading);
		}
		
		checkEnd(locs.size());
		//LCD.clear();
		LCD.drawString("" + locs.size(), 0, 7);
		Delay.msDelay(100);
	}

	@Override
	public void suppress() {
		
	}
	
	//Turns left
		private void moveLeft(){
			pilot.rotate(126);
		}
		//Turns right
		private void moveRight(){
			pilot.rotate(-126);			
		}
		//Continues going forward
		private void moveForward(){
			pilot.forward();
		}
		
	
	private void generateLightValues(){
		leftValue = left.getLightValue();
		rightValue = right.getLightValue();
		//System.out.println("L: " + leftValue + " R: " + rightValue);
	}
	
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
			if(robotDirection == NORTH)
				robotDirection = EAST;
			else
				robotDirection-=1;
		}
		else
			moveForward();
		
		setDirections();
		
	}
	
	private void turnDecision(float leftPosition, float rightPosition){
		if(leftPosition > cellSize && rightPosition > cellSize){ 
			if(leftPosition < rightPosition){
				executeMove(Directions.LEFT);
				locs.updateLocations(heading);
			}else{
				executeMove(Directions.RIGHT);
				locs.updateLocations(heading);
			}
		}else if(leftPosition > cellSize){
			executeMove(Directions.LEFT);
			locs.updateLocations(heading);
		}else if(rightPosition > cellSize){
			executeMove(Directions.RIGHT);
			locs.updateLocations(heading);
		}else{
			executeMove(Directions.LEFT);
			executeMove(Directions.LEFT);
			locs.updateLocations(heading);
		}
		LCD.clear();
		LCD.drawString("h: " + heading, 0, 0);
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
		else if(robotDirection==WEST){
			heading = 180;
			WEST_DISTANCE=ranger.getRange();
			DISTANCE=ranger.getRange();
		}
	}
	
	private void checkEnd(int size){
		if(size == 1){
			Sound.beep();
			LCD.drawString(locs.getPoints(0).getxCoord() + " " + locs.getPoints(0).getyCoord(), 0, 4);
			Delay.msDelay(10000);
			System.exit(0);
		}else if(size == 0){
			LCD.drawString("Error with localisation", 0, 0);
		}
	}
}

