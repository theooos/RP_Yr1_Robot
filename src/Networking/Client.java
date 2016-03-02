package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Client {

	private ClientReceiver receiver;
	private ClientSender sender;
	
	private UUID name;
	
	public Client(String[] args) {
		name = UUID.randomUUID();
		
		System.out.println("Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
		System.out.println("OK!");

		DataInputStream in = connection.openDataInputStream();
		DataOutputStream out = connection.openDataOutputStream();
		
	    sender = new ClientSender(out);
    	receiver = new ClientReceiver(in);
	    sender.start();	    
	    receiver.start();
	    
	    // Ronan(name);
	    // Luyobmir(whatever he needs);
	    // Lyuobmir.start();
	    
	    // TODO Once objects are set up, use a name object.
	    send(name);
	    
 		while (receiver.isAlive()) {			
 			
 		   	// Goes through all available instructions and executes them.
 			Object comm = null;
 			
 		    while((comm = receiver.popCommand()) != null)
 		    {
 		    	String cT = comm.getClass().getName();
 		    	
 		    	if(cT.equals("Move") || cT.equals("Something")){
 		    		// Give it to the motion.
 		    	}
 		    	else if(cT.equals("Somethingelse") || cT.equals("Test")){
 		    		// Give it to the interface.
 		    	}
 		    }
 		}
     	out("MyClient no longer running");
	}
	
	/**
	 * Sends a message to the sender.
	 * @param comm
	 */
	public void send(Object comm){
    	sender.send(comm);
    }

	public static void main(String[] args) {
		new Client(args);
	}
	
	// ********************** HELPER METHODS *********************

	private void out(Object n) {
		System.out.println(""+n);
	}
}
