package three;

import utils.TimeHelper;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Cleavest on 21/4/2024
 */
public class NumIntRecursive {

    public static void main(String[] args) {
        TimeHelper helper = new TimeHelper();
        int size = 100000000;
        int limit = 11000000;

        double step = 1.0 / (double)size;

        Function<Long, Double> func = (i) -> {
            double x = ((double)i+0.5)*step;
            return 4.0/(1.0+x*x);
        };

        helper.start();
        /* create and start thread 0 */

        Recursive mythread = new Recursive(0, size, limit, func);
        mythread.start();

        /* wait for thread 0 */
        double sum = 0;
        try {
            mythread.join();
            sum = mythread.myResult;
        } catch (InterruptedException e) {}

        helper.end();

        double pi = sum * step;

        System.out.printf("sequential program results with %d steps\n", size);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %d millis\n", helper.getTime());
    }
}

class Recursive extends Thread {

    private int myFrom;
    private int myTo;
    private int myLimit;
    public double myResult;
    private final Function<Long, Double> func;

    public Recursive (int from, int to, int limit, Function<Long, Double> func) {
        this.myFrom = from;
        this.myTo = to;
        this.myLimit = limit;
        this.myResult = 0;
        this.func = func;
    }

    public void run() {
        /* do recursion until limit is reached */
        //System.out.println("In thread"+Thread.currentThread().getName());
        int workload = myTo - myFrom;
        if (workload <= myLimit) {
            //System.out.printf("Cutoff %d %d \n",myFrom, myTo);
            myResult = 0;
            for (long i=myFrom; i < myTo; ++i)
            {
                myResult += func.apply(i);
            }
        } else {
            int mid = myFrom + workload / 2;
            //System.out.printf("L %d %d %d \n",myFrom, mid, myLimit);
            Recursive threadL = new Recursive(myFrom, mid, myLimit, func);
            threadL.start();
            //System.out.printf("R %d %d %d \n", mid, myTo, myLimit);
            Recursive threadR = new Recursive(mid, myTo, myLimit, func);
            threadR.start();
            try {
                threadL.join();
                myResult = threadL.myResult;
                threadR.join();
                myResult += threadR.myResult;
            } catch (InterruptedException e) {}
        }
    }
}
