package Networking;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import Objects.Sendable.Move;
import Objects.Sendable.RobotInfo;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Client {

	private ClientReceiver receiver;
	
	private String name;
	
	public Client(String[] args) {
		name = "Alfie";
		
//		System.out.println("Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
//		System.out.println("OK!");

		DataInputStream in = connection.openDataInputStream();
		DataOutputStream out = connection.openDataOutputStream();
		
		ClientSender.setStream(out);
    	receiver = new ClientReceiver(in);  
	    receiver.start();
	    
	    // Ronan(name);
	    // Luyobmir(whatever he needs);
	    // Lyuobmir.start();
	    
	    // TODO Get the robot's proper name.
	    RobotInfo info = new RobotInfo(name, new Point(1,1));
	    ClientSender.send(info);
	    
 		while (receiver.isAlive()) {			
 			
 		   	// Goes through all available instructions and executes them.
 			SendableObject comm = null;
 			
 		    while((comm = receiver.popCommand()) != null)
 		    {
 		    	if(comm instanceof Move){
 		    		// Do blah
 		    	}
 		    	else if(comm instanceof SingleTask){
 		    		// Do blah
 		    	}
 		    }
 		}
//     	out("MyClient no longer running");
	}

	public static void main(String[] args) {
		new Client(args);
	}
	
	// ********************** HELPER METHODS *********************

	private void out(Object n) {
		System.out.println(""+n);
	}
}
