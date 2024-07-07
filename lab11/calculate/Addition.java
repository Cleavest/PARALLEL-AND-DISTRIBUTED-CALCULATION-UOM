import java.rmi.*;

public interface Addition extends Remote {
	
	// Ypografh ths apomakrysmenhs methodoy.
	public double calculate(int operation, int a, int b) throws RemoteException;
}
