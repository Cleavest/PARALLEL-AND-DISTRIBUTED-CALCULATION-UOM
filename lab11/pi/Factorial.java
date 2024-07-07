import java.rmi.*;

public interface Factorial extends Remote {
	
	// Ypografh ths apomakrysmenhs methodoy.
	public double calculatePI(long number) throws RemoteException;
}
