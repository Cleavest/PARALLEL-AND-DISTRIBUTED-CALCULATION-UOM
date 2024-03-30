/**
 * @author Im_IDK
 */
class VectorAdd
{
    public static void main(String args[])
    {
        int size = 100000;
        int numThreads = Runtime.getRuntime().availableProcessors();

        double[] a = new double[size];
        double[] b = new double[size];
        double[] c = new double[size];
        for(int i = 0; i < size; i++) {
            a[i] = 0.0;
            b[i] = 1.0;
            c[i] = 0.5;
        }

        long start = System.currentTimeMillis();

        VectorGroupThread threads[] = new VectorGroupThread[numThreads];

        for(int i = 0; i < numThreads; i++)
        {
            threads[i] = new VectorGroupThread(i, numThreads, a, b,c ,size);
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("time in ms = "+ elapsedTimeMillis);

     //for debugging
    for(int i = 0; i < size; i++)
        System.out.println(a[i]);
    }



}

class VectorGroupThread extends Thread
{
    private double [] a;
    private double [] b;
    private double [] c;
    private int myStart;
    private int myStop;

    // constructor
    public VectorGroupThread(int myId, int numThreads, double[] a, double[] b, double[] c, int size) {
        this.a = a;
        this.b = b;
        this.c = c;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        if (myId == (numThreads - 1)) myStop = size;
    }

    // thread code
    public void run()
    {
        for(int i = myStart; i < myStop; i++)
            a[i] = b[i] + c[i];
    }
}
