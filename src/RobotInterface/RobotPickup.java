package RobotInterface;

import java.awt.Point;

import Objects.Item;
import Objects.SingleTask;
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.util.Delay;

/**
 * The class that allows a user to interact with the robot to pick items up
 *
 */
public class RobotPickup extends Thread {
	
	private Point place;
	private String id;
	private int quantity;
	
	public RobotPickup(SingleTask pickup) {
		Item item = pickup.getItem();
		this.place = new Point(item.getX(), item.getY());
		this.id = "aa"; //item.getID(); //to be added
		this.quantity = pickup.getQuantity();
		
	}
	
	public void run() { //will get started by Theo when in location
		int x = LCD.CELL_WIDTH / 4;
		int y = LCD.CELL_HEIGHT / 2;
		LCD.drawString("I need " + quantity + "x " + id + ".", x, y); //Output message
		int i = quantity;
		int pressed;
		while(i > 0) {
			pressed = Button.waitForAnyPress(); //wait for button to be pressed and store which it is
			LCD.clear();						
			if(pressed == Button.ID_ENTER) { //if it is the enter button
				i--; //decrease the number needed
			}
			else { //otherwise
				LCD.drawString("Press the enter button to add an item", x, y+2);
				Delay.msDelay(2000); //display warning message
				LCD.clear(); 
			}
			LCD.drawString("I need " + i + "x " + id + ".", x, y); //Update on screen how many needed
		}
		LCD.clear();
		//CompleteReport report = new CompleteReport(true, true);
	}
	
	public static void main(String[] args) { //Used entirely for testing my code on a robot
		Button.waitForAnyPress();
		Item testItem = new Item(3, 5, 1.2, 2.5);
		SingleTask task = new SingleTask(testItem, 5);
		RobotPickup p = new RobotPickup(task);
		p.start();
	}
}

