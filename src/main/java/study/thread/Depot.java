package study.thread;

import li.util.Log;

import java.util.Random;

public class Depot {
  public int capacity;
  public int size = 0;

  public Depot(int capacity) {
    this.capacity = capacity;
  }

  public synchronized void consume(int left) throws InterruptedException {
    if (left > capacity) {
      throw new RuntimeException(" too much consume");
    }
    Log.log(Thread.currentThread().getName() + " size = " + size + "  consume=" + left);
    while (left > size) {
      wait();
    }

    size = size - left;

    Log.log(Thread.currentThread().getName() + " size = " + size + "  consume=" + left);
    notifyAll();
  }

  public synchronized void produce(int right) {
    Log.log(Thread.currentThread().getName() + " want=" + right + ",size=" + size);
    while (size + right > capacity) {
      try {
        right = right + size - capacity;
        size = capacity;
        notifyAll();
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    size = size + right;
    Log.log(Thread.currentThread().getName() + " product=" + right + ",size=" + size);
    notifyAll();
  }

  public static void main(String[] args) {

    final Depot depot = new Depot(11);
    Runnable r1 = () -> {
      while (true) {
        try {
          depot.consume(next());
          Thread.sleep(100 * next());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    Runnable r2 = () -> {
      while (true) {
        try {
          depot.produce(next());
          Thread.sleep(100 * next());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    new Thread(r1, "consume ").start();
    new Thread(r2, "p1").start();
    new Thread(r2, "p1______________").start();
  }

  public static int next() {
    Random random = new Random();
    return random.nextInt(10) + 1;

  }

}
