package second;

/**
 * @author Cleavest on 3/5/2024
 */
import java.io.*;

public class ClientProtocol {

    BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

    public String prepareRequest() throws IOException {
        boolean done = false;

        String command = "-1";

        while (!done) {
            System.out.print("Enter command: ");
            command = user.readLine();
            done = checkData(command);
        }

        String request = command;
        return request;
    }

    private boolean checkData(String command) {
         if (command.split(" ").length != 3) {
             System.out.println("length " + command.split(" ").length);
             return false;
         }

        return true;
    }

    public void processReply(String theInput) throws IOException {
        System.out.println(theInput);
    }
}
