package defaultS;

import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	
	public String prepareRequest() throws IOException {
	
		System.out.print("Enter message to send to server:");
		String theOutput = user.readLine();
		return theOutput;
	}

	public void processReply(String theInput) throws IOException {
	
		System.out.println("Message received from server: " + theInput);
	}
}
