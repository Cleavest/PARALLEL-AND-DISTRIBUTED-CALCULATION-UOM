package third;

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
            done = isNumeric(command);
        }

        String request = command;
        return request;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public void processReply(String theInput) throws IOException {
        System.out.println("pi:" + theInput);
    }
}
