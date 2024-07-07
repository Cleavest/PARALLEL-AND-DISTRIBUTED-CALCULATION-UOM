import java.rmi.*;
import java.rmi.server.*;

public class AdditionImpl extends UnicastRemoteObject implements Addition {
	
	private static final long serialVersionUID = 1;

	public AdditionImpl() throws RemoteException {
	}

	@Override
	public double calculate(int operation, int a, int b) throws RemoteException {
		return Operation.values()[operation].apply(a,b);
	}
}
