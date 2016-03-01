package RobotInterface;

import java.awt.Point;
import java.util.List;

import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.util.Delay;

public class DropoffInterface extends Thread {
	
	//private Point place;
	//private List<SingleTask> items;
	
	public DropoffInterface(/*List<SingleTask> i*/) {
		//this.items = i
		//this.place = ?????
	}
	
	public void run() {
		LCD.drawString("I have:", 1, 1);
		/*int y = 2;
		for(SingleTask t : items) {
			LCD.drawString(t.getQuantity() + "x " + t.getItem().getID(), 2, y);
			y++;
		}*/
		LCD.drawString("4x aa", 2, 2); 	//testing
		LCD.drawString("3x ab", 2, 3); 	//testing	<-display what the robot is holding
		LCD.drawString("5x ac", 2, 4);	//testing
		int y = 5;						//testing
		LCD.drawString("Press ENTER", 2, y);  //Display instructions
		int pressed = Button.waitForAnyPress(); //Record the pressed button
		while(pressed != Button.ID_ENTER) {		//if it isn't enter
			pressed = Button.waitForAnyPress();	//keep waiting, until enter is pressed
		}
		//CompleteReport report = new CompleteReport(false, true);
	}
	
	public static void main(String[] args) {	//entirely for testing
		Button.waitForAnyPress();
		DropoffInterface d = new DropoffInterface();
		d.start();
	}
}
