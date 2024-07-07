import java.util.function.BiFunction;

public class NumIntSeqParGet {

    public static void main(String[] args) {

        long numSteps = 1000000000;
        double sum = 0.0;
        int numThreads = Runtime.getRuntime().availableProcessors();
        
        /* start timing */
        long startTime = System.currentTimeMillis();

        NumIntSeqThread threads[] = new NumIntSeqThread[numThreads];
        double step = 1.0 / (double)numSteps;

        BiFunction<Long, Double, Double> func = (i, s) -> {
            double x = ((double)i+0.5)*s;
            return 4.0/(1.0+x*x);
        };

        for (int i = 0; i < numThreads; i++)
        {
            threads[i] = new NumIntSeqThread(i, numThreads, numSteps, step, func);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                sum = sum + threads[i].getMySum();
            } catch (InterruptedException e) {}
        }

        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class NumIntSeqThread extends Thread {
    public double mySum;
    private int myId;
    private int myStart;
    private int myStop;
    private double step;
    private BiFunction<Long, Double, Double> func;

    public NumIntSeqThread(int id, int numThreads, long numSteps, double step, BiFunction<Long, Double, Double> func)
    {
        mySum = 0.0;
        this.func = func;
        myId = id;
        this.step = step;
        myStart = (int) (myId * (numSteps / numThreads));
        myStop = (int) (myStart + (numSteps / numThreads));
        if (myId == (numThreads - 1)) myStop = (int) numSteps;
    }

    public double getMySum() {
        return mySum;
    }

    @Override
    public void run() {
        for (long i=myStart; i < myStop; ++i) {
            mySum += func.apply(i,step);
        }
    }
}
