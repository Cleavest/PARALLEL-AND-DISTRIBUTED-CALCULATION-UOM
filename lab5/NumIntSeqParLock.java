import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

public class NumIntSeqParLock {

    public static void main(String[] args) {

        long numSteps = 100000000;
        int numThreads = Runtime.getRuntime().availableProcessors();
        
        /* start timing */
        long startTime = System.currentTimeMillis();

        NumIntSeqThreadLock threads[] = new NumIntSeqThreadLock[numThreads];
        double step = 1.0 / (double)numSteps;

        BiFunction<Long, Double, Double> func = (i, s) -> {
            double x = ((double)i+0.5)*s;
            return 4.0/(1.0+x*x);
        };

        SumObj sumObj = new SumObj();

        for (int i = 0; i < numThreads; i++)
        {
            threads[i] = new NumIntSeqThreadLock(i, numThreads, numSteps, step, func, sumObj);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        sumObj.printout(numSteps, step, startTime);

    }
}

class NumIntSeqThreadLock extends Thread {
    public double mySum;
    private SumObj globalSum;
    private int myId;
    private int myStart;
    private int myStop;
    private double step;
    private BiFunction<Long, Double, Double> func;

    public NumIntSeqThreadLock(int id, int numThreads, long numSteps, double step, BiFunction<Long, Double, Double> func, SumObj s)
    {
        mySum = 0.0;
        this.func = func;
        this.globalSum = s;
        myId = id;
        this.step = step;
        myStart = (int) (myId * (numSteps / numThreads));
        myStop = (int) (myStart + (numSteps / numThreads));
        if (myId == (numThreads - 1)) myStop = (int) numSteps;
    }


    @Override
    public void run() {
        for (long i=myStart; i < myStop; ++i) {
            mySum += func.apply(i,step);
        }

        globalSum.add(mySum);
    }
}

class SumObj {
    private double sum;
    private Lock lock = new ReentrantLock();

    public SumObj() {sum = 0.0;}

    public void add (double localsum){
        lock.lock();
        try {
            this.sum = this.sum + localsum;
        } finally {
            lock.unlock();
        }
    }

    public void printout(long numSteps,double step, long startTime) {
        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }


}
