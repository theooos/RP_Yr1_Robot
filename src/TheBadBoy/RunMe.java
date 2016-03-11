package TheBadBoy;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import Networking.ClientReceiver;
import Networking.ClientSender;
import Objects.Sendable.DropOffPoint;
import Objects.Sendable.Move;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;
import RobotInterface.RobotInterface;
import RobotMotionControl.TestMovement;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class RunMe {

private ClientReceiver receiver;
	
	private String name;
	
	public RunMe(String[] args) {
		name = "Alfie";
		
//		System.out.println("Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
//		System.out.println("OK!");

		DataInputStream in = connection.openDataInputStream();
		DataOutputStream out = connection.openDataOutputStream();
		
		ClientSender.setStream(out);
    	receiver = new ClientReceiver(in);  
	    receiver.start();
	    
	    RobotInterface theInterface = new RobotInterface(name);	    
//	    // TODO Get the robot's proper name.
//	    RobotInfo info = new RobotInfo(name, new Point(1,1));
//	    ClientSender.send(info);
	    
 		while (receiver.isAlive()) {			
 			
 		   	// Goes through all available instructions and executes them.
 			SendableObject comm = null;
 			
 		    while((comm = receiver.popCommand()) != null)
 		    {
 		    	if(comm instanceof Move){
 		    		TestMovement.move((Move)comm);
 		    	}
 		    	else if(comm instanceof SingleTask || comm instanceof DropOffPoint){
 		    		theInterface.add(comm);
 		    	}
 		    }
 		}
     	out("MyClient no longer running");
	}

	public static void main(String[] args) {
		new RunMe(args);
	}
	
	// ********************** HELPER METHODS *********************

	private void out(Object n) {
		System.out.println(""+n);
	}

}