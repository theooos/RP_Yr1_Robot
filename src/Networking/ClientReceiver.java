package Networking;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Objects.Sendable.Move;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;

public class ClientReceiver extends Thread {

	private boolean alive = true;
	private DataInputStream fromServer;
	private ArrayList<SendableObject> commands = new ArrayList<SendableObject>();

	public ClientReceiver(DataInputStream fromServer) {
		this.fromServer = fromServer;
	}

	/**
	 * Forever trying to read two messages at a time from the server, to deduce
	 * new commands.
	 */
	@Override
	public void run() {
		while (alive) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				System.out.println("INTERRUPTED.");
			}
			
			try {
				String fullComm = fromServer.readUTF();
				Object[] splitComm = Splitter.split(fullComm);
				String type = (String) splitComm[0];
				Object[] objParams = Arrays.copyOfRange(splitComm, 1, splitComm.length);
				figureType(type, objParams);				
				
			} catch (IOException e) {
				out("MyListener noticed the server died. Shutting everything down.");
				System.exit(3);
			} catch (NullPointerException e) {
				// Stops .readUTF from throwing errors.
			}
		}
	}

	/**
	 * Reforms the Strings sent from the server into SendableObjects that are added then added to the commands list.
	 * @param The name of the class.
	 * @param The parameters of that class.
	 */
	private synchronized void figureType(String type, Object[] parameters) {
		SendableObject newObj = null;
		if(type.equals("Move")){
			newObj = new Move((Character)parameters[0], new Point((Integer)parameters[1],(Integer)parameters[2]));
		}
		else if(type.equals("SingleTask")){
			newObj = new SingleTask((String) parameters[0], (Integer) parameters[1]);
		}
		
		if(newObj == null){
			out("Error creating new object. Didn't know how to deal with: " + type);
		}
		else {
			addComm(newObj);
		}
	}
	
	/**
	 * Ends the thread.
	 */
	public void shutdown(){
		alive = false;
	}

	// ************************ ARRAYLIST ACCESSORS **************************
	
	/**
	 * Only done so that access to the commandsForInterface ArrayList is always
	 * synchronised.
	 * 
	 * @param comm The command to add.
	 */
	synchronized private void addComm(SendableObject comm) {
		commands.add(comm);
	}
	
	/**
	 * Takes the next message from the message list.
	 * @return
	 */
	synchronized public Object popCommand() {
		if (commands.isEmpty()) {
			return null;
		} else {
			Object comm = commands.get(0);
			commands.remove(0);
			return comm;
		}
	}

	// ******************** HELPER COMMANDS **********************
	
	private void out(Object n) {
		System.out.println("" + n);
	}
}
