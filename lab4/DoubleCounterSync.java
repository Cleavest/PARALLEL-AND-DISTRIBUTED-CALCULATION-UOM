public class DoubleCounterSync {

    public static void main(String[] args) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        DoubleCounter doubleCounter = new DoubleCounter(0,0);
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            if (i % 2 == 0) {
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < 100000; j++) {
                        doubleCounter.incN1();
                    }
                });
            } else {
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < 100000; j++) {
                        doubleCounter.incN2();
                    }
                });
            }
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}

class DoubleCounter {
    private int n1;
    private int n2;
    Object n1_l = new Object();
    Object n2_l = new Object();

    public DoubleCounter(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public void incN1(){
        synchronized (n1_l) {
            n1++;
        }
    }
    public void incN2(){
        synchronized (n2_l) {
            n2++;
        }
    }

    public int getN1() {
        return n1;
    }

    public int getN2() {
        return n2;
    }
}