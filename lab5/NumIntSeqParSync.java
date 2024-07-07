import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

public class NumIntSeqParSync {

    public static void main(String[] args) {

        long numSteps = 100000000;
        int numThreads = Runtime.getRuntime().availableProcessors();
        
        /* start timing */
        long startTime = System.currentTimeMillis();

        NumIntSeqThreadSync threads[] = new NumIntSeqThreadSync[numThreads];
        double step = 1.0 / (double)numSteps;

        BiFunction<Long, Double, Double> func = (i, s) -> {
            double x = ((double)i+0.5)*s;
            return 4.0/(1.0+x*x);
        };

        SumObj2 sumObj = new SumObj2();

        for (int i = 0; i < numThreads; i++)
        {
            threads[i] = new NumIntSeqThreadSync(i, numThreads, numSteps, step, func, sumObj);
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

class NumIntSeqThreadSync extends Thread {
    public double mySum;
    private SumObj2 globalSum;
    private int myId;
    private int myStart;
    private int myStop;
    private double step;
    private BiFunction<Long, Double, Double> func;

    public NumIntSeqThreadSync(int id, int numThreads, long numSteps, double step, BiFunction<Long, Double, Double> func, SumObj2 s)
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

class SumObj2 {
    private double sum;

    public SumObj2() {sum = 0.0;}

    public synchronized void add (double localsum){
        this.sum = this.sum + localsum;
    }

    public synchronized void printout(long numSteps,double step, long startTime) {
        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }


}
