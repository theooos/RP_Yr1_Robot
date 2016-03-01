package RobotInterface;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Objects.Item;
import Objects.SingleTask;
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.util.Delay;

public class RobotDropOff extends Thread {
	
	private Point place;
	private List<SingleTask> items;
	
	public RobotDropOff(List<SingleTask> i) {
		this.items = i;
		//this.place = ?????
	}
	
	public void run() {
		LCD.drawString("I have:", 1, 1);
		int y = 2;
		for(SingleTask t : items) {
			LCD.drawString(t.getQuantity() + "x "/* + t.getItem().getID() //need to add*/, 2, y);
			y++;
		}
		/*LCD.drawString("4x aa", 2, 2); 	//testing
		LCD.drawString("3x ab", 2, 3); 	//testing	<-display what the robot is holding
		LCD.drawString("5x ac", 2, 4);	//testing
		int y = 5;						//testing*/
		LCD.drawString("Press ENTER", 2, y);  //Display instructions
		int pressed = Button.waitForAnyPress(); //Record the pressed button
		while(pressed != Button.ID_ENTER) {		//if it isn't enter
			pressed = Button.waitForAnyPress();	//keep waiting, until enter is pressed
		}
		//CompleteReport report = new CompleteReport(false, true);
	}
	
	public static void main(String[] args) {	//entirely for testing
		Button.waitForAnyPress();
		Item testItem1 = new Item(3, 5, 1.2, 2.5);
		SingleTask task1 = new SingleTask(testItem1, 5);
		Item testItem2 = new Item(2, 7, 1.8, 3.0);
		SingleTask task2 = new SingleTask(testItem2, 3);
		Item testItem3 = new Item(4, 2, 1.0, 2.0);
		SingleTask task3 = new SingleTask(testItem3, 4);
		List<SingleTask> tasks = new ArrayList<SingleTask>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		RobotDropOff d = new RobotDropOff(tasks);
		d.start();
	}
}
