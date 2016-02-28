package RobotInterface;

import java.awt.Point;

import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.util.Delay;


public class PickupInterface extends Thread {
	
	//private Point place;
	private String id;
	private int quantity;
	
	public PickupInterface(/*SingleTask pickup*/) {
		/*Item item = pickup.getItem();
		this.place = item.getLocation();
		this.id = item.getID();
		this.quantity = pickup.getQuantity();*/
		
		//this.place = new Point(3, 4);
		this.id = "aa";
		this.quantity = 5;
	}
	
	public void run() {
		int x = LCD.SCREEN_WIDTH / 4;
		int y = LCD.SCREEN_HEIGHT / 2;
		LCD.drawString("I need " + quantity + "x " + id + ".", x, y);
		int i = quantity;
		int pressed;
		while(i > 0) {
			pressed = Button.waitForAnyPress();
			LCD.clear();
			if(pressed = Button.ID_ENTER) {
				i--;
			}
			else {
				LCD.drawString("Press the enter button to add an item");
				Delay.msDelay(2000);
				LCD.clear();
			}
			LCD.drawString("I need " + i + "x " + id + ".", x, y);
		}
		LCD.clear();
		//CompleteReport report = new CompleteReport(true, true);
	}
	
	public static void main(String[] args) {
		Button.waitForAnyPress();
		PickupInterface p = new PickupInterface();
		p.start();
	}
}
