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
		LCD.drawString("3x ab", 2, 3); 	//testing
		LCD.drawString("5x ac", 2, 4);	//testing
		int y = 5;						//testing
		LCD.drawString("Press ENTER to drop these items off.", 2, y);
		int pressed = Button.waitForAnyPress();
		while(pressed != Button.ID_ENTER) {
			pressed = Button.waitForAnyPress();
		}
		//CompleteReport report = new CompleteReport(false, true);
	}
	
	public static void main(String[] args) {
		Button.waitForAnyPress();
		DropoffInterface d = new DropoffInterface();
		d.start();
	}
}
