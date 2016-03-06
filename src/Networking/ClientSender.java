package Networking;

import java.io.DataOutputStream;
import java.io.IOException;

import Objects.Sendable.SendableObject;

public class ClientSender {
	
	private static DataOutputStream toServer;
	
	public static void setStream(DataOutputStream stream){
		toServer = stream;
	}
	
	/**
	 * Only done so that access to the commands ArrayList is always synchronised.
	 * @param comm The command to add.
	 * @throws IOException 
	 */
	synchronized static public void send(SendableObject comm) throws IOException{
		String dissolve = comm.parameters();
		toServer.writeUTF(dissolve);
	}

}
