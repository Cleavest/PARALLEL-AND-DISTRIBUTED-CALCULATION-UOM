public class SharedCounterArrayArgsSync {


    public static void main(String[] args) {
		int end = 1000;
    	int[] array = new int[end];
		int numThreads = 4;

		CounterThread threads[] = new CounterThread[numThreads];
		
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(end, array);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
		check_array (end, numThreads, array);
    }
     
    static void check_array (int end, int numThreads, int[] array)  {
		int i, errors = 0;

		System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
			if (array[i] != numThreads*i) {
				errors++;
				System.out.printf("%d: %d should be %d\n", i, array[i], numThreads*i);
			}         
        }
        System.out.println (errors+" errors.");
    }


    static class CounterThread extends Thread {

		private int end;
		private int[] array;
  	
       public CounterThread(int end, int[] array) {
		this.end = end;
		this.array = array;
       }
  	
       public void run() {
  
            for (int i = 0; i < end; i++) {
				for (int j = 0; j < i; j++)
				{
					synchronized (SharedCounterArrayArgsSync.class) {
						array[i]++;	
					}
				}	
            } 
		}            	
    }
}
  
