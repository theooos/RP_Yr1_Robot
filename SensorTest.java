

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;
/**
 * Class for testing the light values
 * nothing interesting here
 */
public class SensorTest {

	private WheeledRobotSystem system;
	private LightSensor left;
	private LightSensor right;
	private boolean running=true;
public SensorTest(WheeledRobotConfiguration config,LightSensor left,LightSensor right){
		
		this.system= new WheeledRobotSystem(config);
		this.left=left;
		this.right=right;
	}
	public static void main(String[] args){
		
		SensorTest lightTest = new SensorTest((WheeledRobotConfiguration)RobotConfigs.CASTOR_BOT,new LightSensor(SensorPort.S1),new LightSensor(SensorPort.S4));
		lightTest.run();
		
	}
	public void run(){
		int leftValue;
		int rightValue;
		while(running){
			 
			leftValue = left.getLightValue();
			rightValue=(int)(right.getLightValue()*0.8);
			if(leftValue>40)
				leftValue=45;
			else
				leftValue=35;
			if(rightValue>40)
				rightValue=45;
			else
				rightValue=35;
			int error=Math.abs(leftValue-rightValue);
		
		    LCD.drawString(leftValue+" "+rightValue+" "+ error, 0, 0);
		    Delay.msDelay(50);
		}
		
	}
	public void stop(){
		running=false;
	}

}

