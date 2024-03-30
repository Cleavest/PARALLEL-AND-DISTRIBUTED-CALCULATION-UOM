public class SharedCounterArrayArgsWhileSync {

	public static void main(String[] args) {
		int end = 10000;
		int numThreads = 4;

		SharedData data = new SharedData(0, new int[end]);

		CounterThread threads[] = new CounterThread[numThreads];

		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(data, end);
			threads[i].start();
		}

		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		check_array(data, end);
	}

	static void check_array(SharedData data, int end) {
		int errors = 0;

		System.out.println("Checking...");

		for (int i = 0; i < end; i++) {
			if (data.getArray()[i] != 1) {
				errors++;
				System.out.printf("%d: %d should be 1\n", i, data.getArray()[i]);
			}
		}
		System.out.println(errors + " errors.");
	}

	static class CounterThread extends Thread {

		private SharedData data;
		private int end;

		public CounterThread(SharedData data, int end){
			this.data = data;
			this.end = end;
		}

		public void run() {
			while (true) {
				synchronized (SharedCounterArrayArgsWhileSync.class) {
					if (data.getCounter() >= end)
						break;
					data.getArray()[data.getCounter()]++;
					data.inc();
				}
			}
		}
	}
}

class SharedData {
	private int counter;
	private int[] array;

	public SharedData(int counter, int[] array) {
		this.counter = counter;
		this.array = array;
	}

	public int[] getArray() {
		return array;
	}

	public int getCounter() {
		return counter;
	}

	public void inc() {
		counter++;
	}

}