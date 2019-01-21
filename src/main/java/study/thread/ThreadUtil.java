package study.thread;

import li.util.Log;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadUtil {
  @Test
  public void test() throws InterruptedException {
    Thread t = new Thread();
    Thread.State state = t.getState();
    Log.log(state);
    t.run();
    Log.log(t.getState());
    t.join();
    t.start();
    Log.log(t.getState());
    Thread.sleep(100000);
  }

  @Test
  public void test2() {
    final int[] i = {30};
    Runnable r = () -> {

      for (; ; ) {
        synchronized (i) {

          Log.log(i[0]--);
        }
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    for (int j = 0; j < 3; j++) {
      new Thread(r).start();
    }
    while (i[0] > 0) {
      synchronized (i) {

        Thread.yield();
      }
    }


  }

  @Test
  public void test3() throws InterruptedException {
    Object sync = new Object();
    Runnable r = () -> {
      synchronized (sync) {
        Log.log("sleep 1");
        sync.notify();
        Log.log("sleep 1");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Log.log("sleep 2");
      }
    };

    synchronized (sync) {

      new Thread(r).start();
      Log.log("begin wait");
      sync.wait();
      Log.log("after wait");
    }
  }

  @Test
  public void test4() throws InterruptedException {
    Object sync = new Object();
    Runnable r = () -> {
      Log.log("sync 1");
      synchronized (sync) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Log.log("new ");
        sync.notify();
      }
      Log.log("sync 2");
    };
    Runnable r2 = () -> {
      synchronized (sync) {
        Log.log("r2 1");
        Thread t = new Thread(r);
        t.start();
        Log.log(t.getState());
        try {
          t.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Log.log("r2 2");
      }
    };
    new Thread(r2).start();
    Log.log("end");
    Thread.sleep(1000000);
  }

  @Test
  public void test5() {
    Runnable run = () -> {
      Log.log("1");
      while (!Thread.currentThread().isInterrupted()) {


      }
      Log.log("2");
    };

    Thread t = new Thread(run);
    t.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t.interrupt();
  }

  @Test
  public void test6() {
    Runnable r = () -> {
      Thread current = Thread.currentThread();
      Log.log(current + " 1 " + current.isDaemon());
      while (current.isDaemon()) {

      }
      Log.log(current + " 2 " + current.isDaemon());
    };

    for (int i = 0; i < 10; i++) {
      Thread t = new Thread(r);
      t.setDaemon(i % 4 == 0);
      t.start();
    }
  }

  @Test
  public void test7() {
    int capacity = 100;
    int[] size = new int[1];
    size[0] = 0;
    final Object full = new Object();
    final Object empty = new Object();
    Runnable r1 = () -> {
      for (; ; ) {

        synchronized (size) {

          if (size[0] < 1) {
            try {
              size.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

          }
        }
      }
    };
    Runnable r2 = () -> {
    };

  }

  @Test
  public void test8() throws InterruptedException {
    Runnable r = () -> {
      for (; ; ) {
        try {
          m1();
          Log.log("r");
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    new Thread(r).start();
    Thread.sleep(100000);

  }

  private Object sync = new Object();
  private Lock lock = new ReentrantLock();

  private void m1() throws InterruptedException {
    lock.lock();
    Log.log("m");

    Thread.sleep(1000);
    m1();
    lock.unlock();
  }


  @Test
  public void test9() throws InterruptedException {
    Runnable r = () -> {
      for (; ; ) {
        Log.log("interrupt " + Thread.currentThread().isInterrupted());
        if (Thread.currentThread().isInterrupted()) {
          try {
            throw new InterruptedException();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };

    Thread t = new Thread(r);
    t.start();
    Thread.sleep(500);
    t.interrupt();
    Thread.sleep(500);


  }

  private volatile int i = 0;

  @Test
  public void test10() {
    HashSet<Integer> integers = new HashSet<>();
    for (int j = 0; j < 100; j++) {
      new Thread(
          () -> {
            for (int k = 0; k < 1000; k++) {
              i++;
              if (integers.contains(i)) {
                throw new RuntimeException();
              } else {
                integers.add(i);
              }
            }
          }

      ).start();
    }
  }

}
