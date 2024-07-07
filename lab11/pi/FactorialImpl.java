import java.rmi.*;
import java.rmi.server.*;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

// H klash ayth ylopoiei thn apomakrysmenh diepafh Factorial.
public class FactorialImpl extends UnicastRemoteObject implements Factorial {
	
	private static final long serialVersionUID = 1;
	private Hashtable<Long, Double> cache;

	// Kataskeyasths.
	public FactorialImpl() throws RemoteException {
		this.cache = new Hashtable<>();
	}

	@Override
	public double calculatePI(long number) throws RemoteException {

		if (!cache.containsKey(number)) {
			cache.put(number, calculate(number));
		}

		return cache.get(number);
	}

	public static double calculate(long numSteps){
		double sum = 0.0;

		double step = 1.0 / (double)numSteps;
		for (long i=0; i < numSteps; ++i) {
			double x = ((double)i+0.5)*step;
			sum += 4.0/(1.0+x*x);
		}
		return sum * step;
	}
}
