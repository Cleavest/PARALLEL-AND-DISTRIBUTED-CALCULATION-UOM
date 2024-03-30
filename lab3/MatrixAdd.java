/**
 * @author Im_IDK
 */
public class MatrixAdd {

    public static void main(String args[])
    {
        int size = 1000;
        int numThreads = Runtime.getRuntime().availableProcessors();

        double[][] a = new double[size][size];
        double[][] b = new double[size][size];
        double[][] c = new double[size][size];
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) {
                a[i][j] = 0.1;
                b[i][j] = 0.3;
                c[i][j] = 0.5;
            }

        long start = System.currentTimeMillis();

        MatrixGroupThread threads[] = new MatrixGroupThread[numThreads];

        for(int i = 0; i < numThreads; i++)
        {
            threads[i] = new MatrixGroupThread(i, numThreads, a, b,c ,size);
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("time in ms = "+ elapsedTimeMillis);

        // for debugging
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++)
                System.out.print(a[i][j]+" ");
            System.out.println();
        }
    }
}

class MatrixGroupThread extends Thread {

    private double[][] a;
    private double[][] b;
    private double[][] c;
    private int size;
    private int myStart;
    private int myStop;

    public MatrixGroupThread(int myId, int numThreads, double[][] a, double[][] b, double[][] c, int size) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.size = size;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        if (myId == (numThreads - 1)) myStop = size;
    }

    @Override
    public void run()
    {
        for(int i = myStart; i < myStop; i++)
            for (int j = 0; j < size; j++) {
                a[i][j] = b[i][j] + c[i][j];
            }
    }

}
