package RobotInterface;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Objects.Item;
import Objects.SingleTask;
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.util.Delay;

/**
 * The class that will allow the user to interact with the robot to confirm dropping items off
 * @author rkelly
 */
public class RobotDropOff extends Thread {
	
	private Point place;
	private List<SingleTask> items;
	ArrayList<String> buffer;
	
	public RobotDropOff(List<SingleTask> i) {
		this.items = i;
		//this.place = ?????
		//create an array list so items can be stored if they don't fit on the screen:
		buffer = new ArrayList<String>(); 
		Button.RIGHT.addButtonListener(new ButtonListener() {
			int x = 2;					//when the scroll is pressed
			@Override
			public void buttonPressed(Button b) {
				if(!buffer.isEmpty()) {	//if the array list is non-empty
					LCD.scroll(); //scroll
					if(buffer.get(0).equals("Press ENTER")) { //formatting
						x++;
					}
					LCD.drawString(buffer.get(0), x, 7); //add next line from buffer to bottom
					buffer.remove(0); //pop the item off the top
				}
			}

			@Override
			public void buttonReleased(Button b) {
				//
			}
			
		});
	}
	
	public void run() {
		RobotConfirm confirm =  new RobotConfirm();
		boolean correct = confirm.confirmLocation(3, 6); //will store true if location is correct
		if(correct) { //if the location is correct
			LCD.drawString("I have:", 1, 0);
			int y = 1;
			for(SingleTask t : items) {
				if(y <= 7) { //if it can fit on the display
					LCD.drawString(t.getQuantity() + "x " + t.getItemID(), 2, y); //Display a single quantity of items held
				}
				else { //ptherwise, add to buffer
					buffer.add(t.getQuantity() + "x " + t.getItemID());
				}
				y++;
			}
			if(y <= 6) { //if instructions can also fit on the screen
				LCD.drawString("Press ENTER", 3, 6);  //Display instructions
				LCD.drawString("to drop-off", 3, 7);
			}
			else { //otherwise add them separately to buffer
				buffer.add("Press ENTER");
				buffer.add("to drop-off");
			}
			int pressed = Button.waitForAnyPress(); //Record the pressed button
			while(pressed != Button.ID_ENTER) {		//if it isn't enter
				pressed = Button.waitForAnyPress();	//keep waiting, until enter is pressed
			}
			//CompleteReport report = new CompleteReport(false, true);
			LCD.clear();
			System.out.println("CR: f, t"); //testing line
		}
		else { //if the location isn't correct
			//CompleteReport report = new CompleteReport(false, false); //return saying drop-off not completed
			System.out.println("CR: f, f"); //testing line
		}
		Delay.msDelay(1000);
	}
	
	public static void main(String[] args) {	//entirely for testing
		Button.waitForAnyPress();
		String testItem1 = "aa";
		SingleTask task1 = new SingleTask(testItem1, 5);
		String testItem2 = "ab";
		SingleTask task2 = new SingleTask(testItem2, 3);
		String testItem3 = "ac";
		SingleTask task3 = new SingleTask(testItem3, 4);
		SingleTask task4 = new SingleTask("ad", 5);
		SingleTask task5 = new SingleTask("ae", 4);
		SingleTask task6 = new SingleTask("af", 4);
		SingleTask task7 = new SingleTask("ag", 1);
		SingleTask task8 = new SingleTask("ah", 3);
		SingleTask task9 = new SingleTask("ai", 2);
		List<SingleTask> tasks = new ArrayList<SingleTask>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		tasks.add(task4);
		tasks.add(task5);
		tasks.add(task6);
		tasks.add(task7);
		tasks.add(task8);
		tasks.add(task9);
		RobotDropOff d = new RobotDropOff(tasks);
		d.start();
	}
}
