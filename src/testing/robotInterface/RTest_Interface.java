package testing.robotInterface;

import java.awt.Point;

import Objects.Sendable.DropOffPoint;
import Objects.Sendable.SingleTask;
import lejos.nxt.Button;
import lejos.util.Delay;
import robotInterface.RobotInterface;

/**
 * TEST FILE: Cycles through various screens of the robot interface to test that it accepts button input and shows the right info
 */
public class RTest_Interface {

	public static void main(String[] args) {
		
		// Waits for any button to be pressed before starting the tests
		System.out.println("PRESS BUTTON TO START TEST");
		Button.waitForAnyPress();
		
		// Starting the interface
		RobotInterface i = new RobotInterface("TESTING 12345"); //for the initital location input screen
		Delay.msDelay(1000);
		
		// Displaying single tasks
		i.add(new SingleTask("aa", 5, new Point(1, 2))); // It should confirm the location every time it promts to pick-up
		Delay.msDelay(1000);
		i.add(new SingleTask("ac", 3, new Point(6, 6)));
		Delay.msDelay(1000);
		i.add(new SingleTask("ae", 4, new Point(4, 2)));
		Delay.msDelay(1000);
		
		// Displaying the drop off confirmation
		i.add(new DropOffPoint(6, 2)); //drop them off
	}

}
