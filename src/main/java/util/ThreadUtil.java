package util;

import java.util.Random;

public class ThreadUtil {
	private static final Random random = new Random();

	public static void print() {
		print("");
	}

	public static void println(String msg) {
		print(msg + "\n");
	}

	public static void println() {
		print("\n");
	}

	public static void print(String msg) {
		System.out.print(Thread.currentThread().getName() + "|" + msg);
	}

	public static void sleep() {
		sleep(500);
	}

	public static void sleepRandom() {
		sleep(random.nextInt(1000));
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
