package second;

/**
 * @author Cleavest on 26/5/2024
 */
import java.io.*;

public class ClientProtocol {

    BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

    public Request prepareRequest() throws IOException {
        boolean done = false;

        String command = "-1";

        while (!done) {
            System.out.print("Enter command: ");
            command = user.readLine();
            done = checkData(command);
        }

        String[] temp = command.split(" ");
        String opCode = temp[0];
        int num1 = Integer.parseInt(temp[1]);
        int num2 = Integer.parseInt(temp[2]);

        return new Request(opCode, num1 , num2);
    }

    private boolean checkData(String command) {
         if (command.split(" ").length != 3) {
             System.out.println("length " + command.split(" ").length);
             return false;
         }

        return true;
    }

    public void processReply(Reply theInput) throws IOException {
        System.out.println(theInput.getOpcode() + " " + theInput.getValue());
    }
}
