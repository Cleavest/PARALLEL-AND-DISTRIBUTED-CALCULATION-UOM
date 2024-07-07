package third;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cleavest on 3/5/2024
 */
public class ServerProtocol {

    private Hashtable<Long, Double> cache = new Hashtable<>();


    public double processRequest(String theInput){

        long numSteps = Long.parseLong(theInput);

        if (numSteps == -1) {
            return -1;
        }

        if (cache.containsKey(numSteps)) {
            return cache.get(numSteps);
        }

        return calcPI(numSteps);
    }


    public double calcPI(long numSteps){
        double sum = 0.0;

        double step = 1.0 / (double)numSteps;
        /* do computation */
        for (long i=0; i < numSteps; ++i) {
            double x = ((double)i+0.5)*step;
            sum += 4.0/(1.0+x*x);
        }
        double pi = sum * step;

        cache.put(numSteps, pi);

        return pi;
    }
}
