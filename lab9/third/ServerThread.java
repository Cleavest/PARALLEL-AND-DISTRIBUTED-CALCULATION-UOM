package third;

import java.io.*;
import java.net.Socket;

/**
 * @author Cleavest on 26/5/2024
 */
public class ServerThread extends Thread {

    private Socket socket;
    private InputStream is;
    private BufferedReader in;
    private OutputStream os;
    private PrintWriter out;
    private static final String EXIT = "CLOSE";

    public ServerThread(Socket socket)
    {
        this.socket = socket;
        try {
            is = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            os = socket.getOutputStream();
            out = new PrintWriter(os,true);
        }
        catch (IOException e)	{
            System.out.println("I/O Error " + e);
        }
    }

    public void run()
    {
        String inmsg;

        double outmsg;

        try {
            inmsg = in.readLine();
            ServerProtocol app = new ServerProtocol();
            outmsg = app.processRequest(inmsg);
            while(outmsg != -1) {
                out.println(outmsg);
                inmsg = in.readLine();
                outmsg = app.processRequest(inmsg);
            }

            socket.close();
            System.out.println("Data socket closed");

        } catch (IOException e)	{
            System.out.println("I/O Error " + e);
        }
    }
}
