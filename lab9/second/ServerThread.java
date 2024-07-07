package second;

import java.io.*;
import java.net.Socket;

/**
 * @author Cleavest on 26/5/2024
 */
public class ServerThread extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());;
        }
        catch (IOException e)	{
            System.out.println("I/O Error " + e);
        }
    }

    @Override
    public void run() {

        try {

            ServerProtocol app = new ServerProtocol();

            Request req = (Request) in.readObject();
            Reply rep = app.processRequest(req);

            while (!rep.getOpcode().equals("ERROR") && rep.getValue() != -9999) {
                out.writeObject(rep);
                req = (Request) in.readObject();
                rep = app.processRequest(req);
            }

            socket.close();
            System.out.println("Data socket closed");

        } catch (IOException e)	{
            System.out.println("I/O Error " + e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
