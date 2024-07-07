package third;

import java.io.*;
import java.net.Socket;

/**
 * @author Cleavest on 3/5/2024
 */
public class EchoClientTCP {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;
    private static final String EXIT = "-1";

    public static void main(String args[]) throws IOException {

        Socket dataSocket = new Socket(HOST, PORT);

        InputStream is = dataSocket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        OutputStream os = dataSocket.getOutputStream();
        PrintWriter out = new PrintWriter(os,true);

        System.out.println("Connection to " + HOST + " established");

        String inmsg, outmsg;
        ClientProtocol app = new ClientProtocol();

        outmsg = app.prepareRequest();
        while(!outmsg.equals(EXIT)) {
            out.println(outmsg);
            inmsg = in.readLine();
            app.processReply(inmsg);
            outmsg = app.prepareRequest();
        }
        out.println(outmsg);

        dataSocket.close();
        System.out.println("Data Socket closed");

    }
}