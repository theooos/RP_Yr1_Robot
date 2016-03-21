package testing.networking;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import Objects.Direction;
import Objects.Sendable.DropOffPoint;
import Objects.Sendable.RobotInfo;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import main.RunRobot;
import networking.ClientReceiver;
import networking.ClientSender;
import robotInterface.RobotInterface;

public class Test_NW_Robot {

	private String name;
	private ClientReceiver receiver;

	public Test_NW_Robot() {
		System.out.println("TESTING: Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
		System.out.println("OK!");

		DataInputStream in = connection.openDataInputStream();
		DataOutputStream out = connection.openDataOutputStream();

		ClientSender.setStream(out);
		receiver = new ClientReceiver(in);  
		receiver.start();
		
		while (receiver.isAlive()) {			

			// Goes through all available instructions and executes them.
			SendableObject comm = null;
			
			while((comm = receiver.popCommand()) != null)
			{
				if(comm instanceof SingleTask) {
					// Sends object back up to the server
					System.out.println("Successfully received object");
					System.out.println("OBJECT: " + comm.toString());
					System.out.println("SENDING OBJECT BACK TO SERVER");
					ClientSender.send((SingleTask)comm);
					System.out.println("SENT THE ITEM BACK");
				}
				if(comm instanceof RobotInfo) {
					// Sends object back up to the server
					name = ((RobotInfo)comm).getName();
					
					RobotInfo newInfo = new RobotInfo(name, new Point(1,1), Direction.NORTH);
					
					ClientSender.send(newInfo);
				}
				else {
					System.out.println("Test_NW Line59");
					return;
				}
			}
		}
		out("MyClient no longer running");
	}

	/**
	 * Main method to run everything
	 * @param args
	 */
	public static void main(String[] args) {
		new Test_NW_Robot();
	}
	
	// ********************** HELPER METHODS *********************

	/**
	 * Helper command to print out objects for debugging
	 * @param n Object to print
	 */
	private void out(Object n) {
		System.out.println(""+n);
	}

}
