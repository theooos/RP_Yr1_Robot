package RobotInterface;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Objects.Item;
import Objects.SingleTask;
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.util.Delay;

/**
 * The class that will allow the user to interact with the robot to confirm dropping items off
 * @author rkelly
 */
public class RobotDropOff extends Thread {
	
	private Point place;
	private List<SingleTask> items;
	
	public RobotDropOff(List<SingleTask> i) {
		this.items = i;
		//this.place = ?????
	}
	
	public void run() {
		RobotConfirm confirm =  new RobotConfirm();
		boolean correct = confirm.confirmLocation(3, 6); //will store true if location is correct
		if(correct) { //if the location is correct
			LCD.drawString("I have:", 1, 1);
			int y = 2;
			for(SingleTask t : items) {
				LCD.drawString(t.getQuantity() + "x " + t.getItemID(), 2, y); //Display a single quantity of items held
				y++; //increase y value so next print is on line below
			}
			LCD.drawString("Press ENTER", 2, y);  //Display instructions
			int pressed = Button.waitForAnyPress(); //Record the pressed button
			while(pressed != Button.ID_ENTER) {		//if it isn't enter
				pressed = Button.waitForAnyPress();	//keep waiting, until enter is pressed
			}
			//CompleteReport report = new CompleteReport(false, true);
		}
		else { //if the location isn't correct
			//CompleteReport report = new CompleteReport(false, false); //return saying drop-off not completed
		}
	}
	
	public static void main(String[] args) {	//entirely for testing
		Button.waitForAnyPress();
		String testItem1 = "aa";
		SingleTask task1 = new SingleTask(testItem1, 5);
		String testItem2 = "ab";
		SingleTask task2 = new SingleTask(testItem2, 3);
		String testItem3 = "ac";
		SingleTask task3 = new SingleTask(testItem3, 4);
		List<SingleTask> tasks = new ArrayList<SingleTask>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		RobotDropOff d = new RobotDropOff(tasks);
		d.start();
	}
}
