package second;

import java.io.*;
import java.net.Socket;

/**
 * @author Cleavest on 26/5/2024
 */
public class EchoClientTCP {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;
    private static final String EXIT = "CLOSE";

    public static void main(String args[]) throws IOException, ClassNotFoundException {

        Socket dataSocket = new Socket(HOST, PORT);

        ObjectOutputStream clientOutputStream = new
                ObjectOutputStream(dataSocket.getOutputStream());
        ObjectInputStream clientInputStream = new
                ObjectInputStream(dataSocket.getInputStream());

        System.out.println("Connection to " + HOST + " established");

        ClientProtocol app = new ClientProtocol();

        Request req = app.prepareRequest();
        while(!req.getOpcode().equals(EXIT)) {

            clientOutputStream.writeObject(req);
            Reply rep = (Reply)clientInputStream.readObject();
            app.processReply(rep);
            req = app.prepareRequest();
        }

        dataSocket.close();
        System.out.println("Data Socket closed");

    }
}