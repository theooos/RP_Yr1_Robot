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

/**
 * TEST FILE: Starts the bluetooth connection with the server so that an object can be received and sent back
 */
public class RTest_Network {

	private String name;
	private ClientReceiver receiver;

	public RTest_Network() {

		// Starting the bluetooth connectioon
		System.out.println("TESTING: Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
		System.out.println("OK!"); // Connection successful

		// Opening the data streams
		DataInputStream in = connection.openDataInputStream();
		DataOutputStream out = connection.openDataOutputStream();
		ClientSender.setStream(out);
		receiver = new ClientReceiver(in);  
		receiver.start();

		// Whilst the server is alive, wait for objects to be received
		while (receiver.isAlive()) {			

			// Goes through all available instructions and executes them.
			SendableObject comm = null;

			while((comm = receiver.popCommand()) != null)
			{

				// Waits for an instance of SingleTask, which is the dummy object that's being sent down
				if(comm instanceof SingleTask) {

					// Prints that the object was received successfully
					System.out.println("Successfully received object!");
					System.out.println("Received: " + comm.toString());
					System.out.println("SENDING OBJECT BACK TO SERVER!");

					// Sends the object back to the server
					ClientSender.send((SingleTask)comm);
					System.out.println("/n SENT THE ITEM BACK!");
				}

				// RobotInfo is used to instantiate the connection
				if(comm instanceof RobotInfo) {

					// Sets the name of the robot
					name = ((RobotInfo)comm).getName();

					// Sends it back up to the server
					RobotInfo newInfo = new RobotInfo(name, new Point(1,1), Direction.NORTH);
					ClientSender.send(newInfo);
				}
				else {
					System.out.println("RTest_Network: Line ~71"); // DEBUG Line
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
		new RTest_Network();
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
