import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedCounterArrayArgsWhileLock {

	public static void main(String[] args) {
		int end = 10000;
		int numThreads = 4;
		Lock lock = new ReentrantLock();

		SharedData data = new SharedData(0, new int[end]);

		CounterThread threads[] = new CounterThread[numThreads];

		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(data, end, lock);
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
		private Lock lock;

		public CounterThread(SharedData data, int end, Lock lock){
			this.data = data;
			this.end = end;
			this.lock = lock;
		}

		public void run() {
			while (true) {
				lock.lock();
				try {
					if (data.getCounter() >= end)
						break;
					data.getArray()[data.getCounter()]++;
					data.inc();
				} finally {
					lock.unlock();
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