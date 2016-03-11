package RobotInterface;

import java.util.ArrayList;

import Networking.ClientSender;
import Objects.Sendable.CompleteReport;
import Objects.Sendable.DropOffPoint;
import Objects.Sendable.SingleTask;
import Objects.Sendable.StartUpItem;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.util.Delay;

/**
 * The class that handles the robot screen and user interaction to pick-up and drop-off items
 * @author rkelly
 *
 */
//Many errors due to dependent classes not being available
//Many commented lines - all to do with communication, to be sorted 10/3
public class RobotInterface {
	
	private ArrayList<String> itemsHeld;
	
	public RobotInterface(String robotName) {
		itemsHeld = new ArrayList<String>();
		StartUpItem sui = getDetails(robotName);
		ClientSender.send(sui);
	}
	
	/**
	 * Output to the screen to take in the location and direction of the robot
	 * @param name the robot's name
	 * @return A StartUpItem containing name, x, y and direction
	 */
	private StartUpItem getDetails(String name) {
		int xVal = 0; //initial values of x, y and location
		int yVal = 0;
		char d = 'N';
		LCD.drawString("x: " + xVal, 2, 1); //output the instructions to the screen
		LCD.drawString("y: " + xVal, 2, 3);
		LCD.drawString("Direction: " + d, 2, 5);
		int which = 0; //indicator
		int pressed;
		while (which < 3) { //while not all details have been confirmed
			pressed = Button.waitForAnyPress(); //wait for a button press
			if(pressed == Button.ID_RIGHT) { //if the user is increasing the value
				if(which == 0) { //if we are currently editing x
					LCD.clear(1);
					xVal++; //increase
					LCD.drawString("x: " + xVal, 2, 1);
				}
				else if(which == 1) { //if we are currently editing y
					LCD.clear(3);
					yVal++; //increase
					LCD.drawString("y: " + yVal, 2, 3);
				}
				else if(which == 2) { //if we are currently editing direction
					LCD.clear(5);
					if(d == 'N') { //change appropriately
						d = 'E';
					}
					else if(d == 'E') {
						d = 'S';
					}
					else if(d == 'S') {
						d = 'W';
					}
					else {
						d = 'N';
					}
					LCD.drawString("Direction: " + d, 2, 5);
				}
			}
			else if(pressed == Button.ID_LEFT) { //if the user is decreasing the value
				if(which == 0) { //see above part of if statement
					LCD.clear(1);
					xVal--;
					LCD.drawString("x: " + xVal, 2, 1);
				}
				else if(which == 1) {
					LCD.clear(3);
					yVal--;
					LCD.drawString("y: " + yVal, 2, 3);
				}
				else if(which == 2) {
					LCD.clear(5);
					if(d == 'N') {
						d = 'W';
					}
					else if(d == 'E') {
						d = 'N';
					}
					else if(d == 'S') {
						d = 'E';
					}
					else {
						d = 'S';
					}
					LCD.drawString("Direction: " + d, 2, 5);
				}
			}
			else if(pressed == Button.ID_ENTER) { //if the information is being confirmed
				which++; //move onto the next detail
			}
			else { //if we want to edit the previous information
				which--; //move to the previous detail
			}
		}
		LCD.clear();
		return new StartUpItem(name, xVal, yVal, d);
	}
	
	/**
	 * Add a n Object to the interface so a pick-up or drop-off can be executed
	 * @param command the Object to add
	 */
	public void add(Object command) {
		SingleTask st;
		DropOffPoint dp;
		if(command instanceof SingleTask) {
			st = (SingleTask) command;
			pickup(st);
		}
		else if(command instanceof DropOffPoint) {
			dp = (DropOffPoint) command;
			dropoff(dp);
		}
	}
	/**
	 * Allow the user to interact with the robot to drop-off items
	 * @param where
	 */
	private void dropoff(DropOffPoint where) {
		ArrayList<String> buffer = new ArrayList<String>();
		Button.RIGHT.addButtonListener(new ButtonListener() {
			int x = 2;				//when the scroll is pressed
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
		boolean correct = confirm(where.getX(), where.getY());
		if(correct) { //if the location is correct
			LCD.drawString("I have:", 1, 0);
			int y = 1;
			for(String t : itemsHeld) {
				if(y <= 7) { //if it can fit on the display
					LCD.drawString(t, 2, y); //Display a single quantity of items held
				}
				else { //otherwise, add to buffer
					buffer.add(t);
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
			CompleteReport report = new CompleteReport(false, true);
			ClientSender.send(report);
			LCD.clear();
			while(!itemsHeld.isEmpty()) {
				itemsHeld.remove(0);
			}
		}
		else { //if the location isn't correct
			CompleteReport report = new CompleteReport(false, false); //return saying drop-off not completed
			ClientSender.send(report);
		}
		LCD.clear();
	}
	
	/**
	 * Allow the user to interact with the robot to pick-up items
	 * @param item
	 */
	private void pickup(SingleTask item) {
		String id = item.getItemID();
		int quantity = item.getQuantity();
		boolean correct = confirm((int)item.getLocation().getX(), (int)item.getLocation().getY());
		if(correct) {		//^ dummy data as I don't know currently how I will get the location
			int x = 2;
			int y = 3;
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
					LCD.drawString("Press enter", 3, y+1); //Instruction
					LCD.drawString("to add", 5, y+2);
					Delay.msDelay(1000); //display warning message
					LCD.clear(); 
				}
				LCD.drawString("I need " + i + "x " + id + ".", x, y); //Update on screen how many needed
			}
			itemsHeld.add(quantity + "x " + id);
			LCD.clear();
			CompleteReport report = new CompleteReport(true, true); //to add to CommandHolder
			ClientSender.send(report);
		}
		else { //if the location is incorrect, send a report saying the pickup wasn't completed
			CompleteReport report = new CompleteReport(true, false);
			ClientSender.send(report);
		}
		LCD.clear();
	}
	
	/**
	 * Allow the user to confirm that the robot is in the correct location (before picking up or dropping off)
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true if the location is correct, false otherwise
	 */
	private boolean confirm(int x, int y) {
		boolean confirmed = false;
		boolean ySelected = true;
		int pressed;
		while(!confirmed) { //while the location hasn't been confirmed
			LCD.drawString("Am I at (" + x + ", " + y + ")?", 1, 3); //output asking if it is correct
			if(ySelected) {	//if yes is currently selected
				LCD.drawString("-> Y / N", 3, 5); //clearly display that
			}
			else {
				LCD.drawString("Y / N <-", 6,  5); //display a pointer to no
			}
			pressed = Button.waitForAnyPress(); //wait for a button to be pressed
			if(pressed == Button.ID_ENTER) { //if the enter button is pressed
				confirmed = true;	//confirm location (don't loop again)
			}
			else if(pressed == Button.ID_LEFT) { //if the left arrow button is pressed
				ySelected = true; //yes is currently selected
			}
			else if(pressed == Button.ID_RIGHT) { //if the right arrow button is pressed
				ySelected = false; //no is currently selected
			}
			LCD.clear(); //clear the screen after each loop
		}
		return ySelected; //after confirmation, return whether yes was selected
	}

	/*public static void main(String[] args) { //entirely used for testing
		Button.waitForAnyPress();
		CommandHolder h = new CommandHolder();
		RobotInterface i = new RobotInterface("dshfjdshf", h);
		Delay.msDelay(1000);
		i.add(new SingleTask("aa", 5, 1, 2));
		Delay.msDelay(1000);
		i.add(new SingleTask("ac", 3, 6, 6));
		Delay.msDelay(1000);
		i.add(new SingleTask("ae", 4, 4, 2));
		Delay.msDelay(1000);
		i.add(new DropoffPoint(6, 2));
		
	}*/

}
