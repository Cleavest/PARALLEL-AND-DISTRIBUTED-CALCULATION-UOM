package two;

import utils.TimeHelper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Cleavest on 13/4/2024
 */
public class MasterWorkerLock {

    static int totalTasks; // αριθμός εργασιών
    static int nThreads; // αριθμός νημάτων
    static int tasksAssigned = -1; // διαμοιραζόμενη μεταβλητή μετρητή εργασιών
    static Lock lock = new ReentrantLock();
    static boolean[] prime;
    static int size;

    public static void main(String[] args)
    {
        size = 1000000000;
        int limit = (int)Math.sqrt(size) + 1;
        totalTasks = limit;
        prime = new boolean[size+1];
        for (int i = 2; i <= size; i++) {
            prime[i] = true;
        }
        nThreads = 10;

        // Δημιουργία και αρχικοποίηση πίνακα
        double[] a = new double[totalTasks];
        for(int i=0; i<totalTasks; i++)
        {
            a[i] = i;
        }

        TimeHelper timeHelper = new TimeHelper();
        timeHelper.start();

        // Δημιουργία νημάτων εργαζομένων
        Thread[] threads = new Thread[nThreads];
        for (int i = 0; i < threads.length; ++i)
        {
            threads[i] = new Thread(new Worker(a,i));
        }

        // Εκκίνηση νημάτων εργαζομένων
        for (int i = 0; i < threads.length; ++i)
        {
            threads[i].start();
        }

        // Αναμονή τερματισμού νημάτων εργαζομένων
        for (int i = 0; i < threads.length; ++i)
        {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {
                System.err.println("this should not happen");
            }
        }

        int count = 0;
        for(int i = 2; i <= size; i++)
            if (prime[i]) {
                //System.out.println(i);
                count++;
            }

        timeHelper.end();

        System.out.println("number of primes "+count);
        System.out.println("time in ms = "+ timeHelper.getTime());
    }

    //  Κρίσιμo τμήμα για την διανομή εργασιών
    private static int getTask()
    {
        lock.lock();
        try {
            // Διανέμει μια εργασία (στοιχείο πίνακα)
            if (++tasksAssigned < totalTasks)
                return tasksAssigned;
            else
                return -1;
        } finally {
            lock.unlock() ;
        }
    }

    // Κώδικας που εκτελεί το κάθε νήμα - εργαζόμενος
    private static class Worker implements Runnable
    {

        private int myID;
        private double[] table;

        //
        public Worker(double[] array, int myID)
        {
            this.myID = myID;
            table = array;
        }

        public void run()
        {
            int element;
            // Λαμβάνει εργασία (ή στοιχείο πίνακα) αν υπάρχει
            while ((element = getTask()) > 0)
            {

                // Υπολογίζει την τετραγωνική ρίζα του στοιχείου που έλαβε
                int p = element;


//                for (int i = p*p; i <= totalTasks; i += p) {
//                    if (i == 0) {
//                        System.out.println(totalTasks);
//                        break;
//                    }
//                };

                if(prime[p])
                {
                    // Update all multiples of p
                    for (int i = p*p; i <= size; i += p)
                    {
                        prime[i] = false;
                    }

                }
            }
            System.out.println("worker " + myID + " done ");
        }
    }
}
