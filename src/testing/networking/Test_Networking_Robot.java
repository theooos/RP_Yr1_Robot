package testing.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import Objects.Sendable.DropOffPoint;
import Objects.Sendable.Move;
import Objects.Sendable.RobotInfo;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import main.RunRobot;
import networking.ClientReceiver;
import networking.ClientSender;
import robotInterface.RobotInterface;
import robotMotionControl.RobotMotion;

public class Test_Networking_Robot {

	private String name;
	private ClientReceiver receiver;
	private RobotInterface theInterface;

	public Test_Networking_Robot() {
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
				if(comm instanceof SingleTask || comm instanceof DropOffPoint) {
					// Sends object back up to the server
					System.out.println("Successfully received object");
					System.out.println("OBJECT: " + comm.toString());
					System.out.println("SENDING OBJECT BACK TO SERVER");
					ClientSender.send(comm);
				}
				else {
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
		new RunRobot(args);
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
