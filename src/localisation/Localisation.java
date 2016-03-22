package localisation;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.DifferentialDriveRobot;

//Main class
public class Localisation {


	public static void main(String[] args){
		
		final double speed = 0.18;
		final int threshold = 42;
		//Follows a straight black line
		WheeledRobotConfiguration OUR_BOT = new WheeledRobotConfiguration(0.056f, 0.120f, 0.230f, Motor.B, Motor.C);
		DifferentialPilot pilot = new DifferentialDriveRobot(OUR_BOT).getDifferentialPilot();
		LightSensor left = new LightSensor(SensorPort.S1);
		LightSensor right = new LightSensor(SensorPort.S3);
		OpticalDistanceSensor range = new OpticalDistanceSensor(SensorPort.S2);
		
		Behavior line = new LineFollow(pilot, left, right, speed, threshold);
		//Detects junctions and makes one move per junction
		Behavior junction = new JunctionDetection(pilot, left, right, speed, range, threshold);
		
		Arbitrator arby = new Arbitrator(new Behavior[] {line,junction});
		arby.start();
	}
}

