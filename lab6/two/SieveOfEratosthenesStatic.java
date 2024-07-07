package two;

import utils.TimeHelper;

import java.util.function.Function;

/**
 * @author Cleavest on 13/4/2024
 */
public class SieveOfEratosthenesStatic {

    public static void main(String[] args)
    {
        TimeHelper helper = new TimeHelper();
        int numThreads = Runtime.getRuntime().availableProcessors();
        int size = 0;
        if (args.length != 1) {
            System.out.println("Usage: java SieveOfEratosthenes <size>");
            System.exit(1);
        }

        try {
            size = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException nfe) {
            System.out.println("Integer argument expected");
            System.exit(1);
        }
        if (size <= 0) {
            System.out.println("size should be positive integer");
            System.exit(1);
        }

        // get current time
        helper.start();
        SharedData data = new SharedData(size);

        SieveOfEratosthenesThread[] threads = new SieveOfEratosthenesThread[numThreads];

        int limit = (int)Math.sqrt(size) + 1;

        for (int i = 0; i < numThreads; i++)
        {
            threads[i] = new SieveOfEratosthenesThread(i, numThreads, size,limit, data);
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

class SharedData {
    boolean[] prime;

    public SharedData(int size) {
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
        prime[index] = value;
    }

}

class SieveOfEratosthenesThread extends Thread {
    private int myId;
    private int myStart;
    private int myStop;
    private int size;
    private SharedData data;

    public SieveOfEratosthenesThread(int id, int numThreads, int size, long limit, SharedData data) {
        this.size = size;
        this.data = data;
        myId = id;
        myStart = (int) (myId * (limit / numThreads));
        myStop = (int) (myStart + (limit / numThreads));
        if (myId == (numThreads - 1)) myStop = (int) limit;
    }

    @Override
    public void run() {
        for (int p = myStart; p <= myStop; p++)
        {
            // If prime[p] is not changed, then it is a prime
//            if(data.isPrime(p))
//            {
//                // Update all multiples of p
//                for (int i = p*p; i <= size; i += p)
//                {
//                    data.setPrime(i, false);
//                }
//
//            }

            if (data.isPrime(p)) {
                Reference.calc(p,size, i -> {
                    data.setPrime(i, false);
                });
            }
        }
    }
}
