package testing.robotInterface;

import java.awt.Point;

import Objects.Sendable.DropOffPoint;
import Objects.Sendable.SingleTask;
import lejos.nxt.Button;
import lejos.util.Delay;
import robotInterface.RobotInterface;

public class Test_robotInt {

	public Test_robotInt() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Button.waitForAnyPress();
		RobotInterface i = new RobotInterface("dshfjdshf"); //for the initital location input screen
		Delay.msDelay(1000);
		i.add(new SingleTask("aa", 5, new Point(1, 2))); //confirm location every time
		Delay.msDelay(1000);
		i.add(new SingleTask("ac", 3, new Point(6, 6)));
		Delay.msDelay(1000);
		i.add(new SingleTask("ae", 4, new Point(4, 2))); //give many single tasks
		Delay.msDelay(1000);
		i.add(new DropOffPoint(6, 2)); //drop them off
	}

}
