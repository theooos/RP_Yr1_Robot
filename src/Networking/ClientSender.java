package Networking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;

public class ClientSender extends Thread {
	
	private DataOutputStream toServer;
	private ArrayList<Object> objectsToPuppet = new ArrayList<Object>();

	public ClientSender(DataOutputStream toServer) {
		this.toServer = toServer;
	}
	
	/* Senders and receivers must always send two objects at a time.
	 * Class name first, and then the JSON object.
	 */
	@Override
	public void run(){
		while(true){
			try {
				/* 
				 * Uses Google's GSON to convert to JSON, instead of spending time
				 * on making all commands serializable ourself.
				 */
				Gson gson = new Gson();
				Object comm = popCommand();
				String className = comm.getClass().getName();
				String commString = gson.toJson(comm);
				
				if (commString != null){
					toServer.writeUTF(className);
					toServer.writeUTF(commString);
				}
			} catch (IOException e) {
				out("MyListener: Disconnected from server. Shutting down.");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gets the next command stored in objectsToPuppet
	 * @return command
	 */
	synchronized private Object popCommand(){
		if(objectsToPuppet.isEmpty()){
			return null;
		}
		else{
			Object comm = objectsToPuppet.get(0);
			objectsToPuppet.remove(0);
			return comm;
		}
	}
	
	/**
	 * Only done so that access to the commands ArrayList is always synchronised.
	 * @param comm The command to add.
	 */
	synchronized public void addComm(Object comm){
		objectsToPuppet.add(comm);
	}

	// Helper command
	private void out(Object n) {
		System.out.println(""+n);
	}

}
