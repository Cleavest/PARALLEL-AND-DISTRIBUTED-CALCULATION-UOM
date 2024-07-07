package two;

import utils.TimeHelper;

/**
 * @author Cleavest on 13/4/2024
 */
public class SieveOfEratosthenesCyclic {

    public static void main(String[] args)
    {
        TimeHelper helper = new TimeHelper();
        int numThreads = Runtime.getRuntime().availableProcessors();
        int size = 1000000000;
        if (args.length != 1) {
            System.out.println("Usage: java SieveOfEratosthenes <size>");
            System.exit(1);
        }

//        try {
//            size = Integer.parseInt(args[0]);
//        }
//        catch (NumberFormatException nfe) {
//            System.out.println("Integer argument expected");
//            System.exit(1);
//        }
        if (size <= 0) {
            System.out.println("size should be positive integer");
            System.exit(1);
        }

        // get current time
        helper.start();
        SharedDataCyclic data = new SharedDataCyclic(size);

        SieveOfEratosthenesCyclicThread[] threads = new SieveOfEratosthenesCyclicThread[numThreads];

        int limit = (int)Math.sqrt(size) + 1;

        for (int i = 0; i < numThreads; i++)
        {
            threads[i] = new SieveOfEratosthenesCyclicThread(i, numThreads, limit,size, data);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        // get current time and calculate elapsed time
        helper.end();

        int count = 0;
        for(int i = 2; i <= size; i++)
            if (data.getPrime()[i]) {
                //System.out.println(i);
                count++;
            }

        System.out.println("number of primes "+count);
        System.out.println("time in ms = "+ helper.getTime());
    }
}

class SharedDataCyclic {
    boolean[] prime;

    public SharedDataCyclic(int size) {
        this.prime = new boolean[size+1];
        fill(size);
    }

    public void fill(int size){
        for(int i = 2; i <= size; i++)
            prime[i] = true;
    }

    public synchronized boolean isPrime(int p){
        return prime[p];
    }

    public boolean[] getPrime() {
        return prime;
    }

    public void setPrime(int index, boolean value) {
        //System.out.println(index);
        prime[index] = value;
    }

}

class SieveOfEratosthenesCyclicThread extends Thread {
    private int myRank;
    private int numThreads;
    private int limit;
    private int size;
    private SharedDataCyclic data;

    public SieveOfEratosthenesCyclicThread(int myRank, int numThreads, int limit, int size, SharedDataCyclic data) {
        this.myRank = myRank;
        this.numThreads = numThreads;
        this.limit = limit;
        this.size = size;
        this.data = data;
    }

    @Override
    public void run() {
        for(int p = myRank; p < limit; p += numThreads) {
            if(data.isPrime(p))
            {
                // Update all multiples of p
                for (int i = p*p; i <= size; i += p)
                {
                    data.setPrime(i, false);
                }

            }
        }
    }
}

