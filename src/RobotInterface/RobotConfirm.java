package RobotInterface;

import lejos.nxt.Button;
import lejos.nxt.LCD;

/**
 * A class used only by robot interface classes to confirm that the location is correct
 * @author rkelly
 */
public class RobotConfirm {
	private boolean confirmed;
	private boolean ySelected;
	private int pressed;
	
	/**
	 * Output to the screen asking whether the robot is in the correct location and await user input
	 * @param x the x co-ordinate of the location
	 * @param y the y co-ordinate of the location
	 * @return true if the location is correct, false otherwise
	 */
	public boolean confirmLocation(int x, int y) {
		confirmed = false;
		ySelected = true;
		while(!confirmed) { //while the location hasn't been confirmed
			LCD.drawString("Am I at (" + x + ", " + y + ")?", 1, 3); //output asking if it is correct
			if(ySelected) {	//if yes is currently selected
				LCD.drawString("-> Y / N", 2, 5); //clearly display that
			}
			else {
				LCD.drawString("Y / N <-", 5,  5); //display a pointer to no
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
}
