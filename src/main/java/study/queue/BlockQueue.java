package study.queue;

import li.util.Log;
import li.util.ObjectUtil;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author li
 */
public class BlockQueue<T> {

  private Object[] items;
  private Lock lock;
  private Condition notEmpty;
  private Condition notFull;
  private int index;
  private int count;
  private int capacity;

  public BlockQueue(int capacity) {
    lock = new ReentrantLock();
    items = new Object[capacity];
    notEmpty = lock.newCondition();
    notFull = lock.newCondition();
    index = -1;
    count = 0;
    this.capacity = capacity;
  }

  public void add(T t) throws InterruptedException {
    Log.log("before add " + this);
    ObjectUtil.forbidNull(t);
    lock.lockInterruptibly();
    try {

      while (count == capacity) {
        notFull.await();
      }
      index++;
      if (index == capacity) {
        index = 0;
      }
      items[index] = t;
      count++;
      notEmpty.signalAll();
    } finally {
      lock.unlock();
    }
    Log.log("after add " + this);
  }

  public void remove() throws InterruptedException {
    Log.log("--------------------------");
    Log.log("before remove " + this);
    lock.lockInterruptibly();
    try {
      while (count == 0) {
        notEmpty.await();
      }
      items[index] = null;
      index--;
      count--;
      notFull.signalAll();
    } finally {
      lock.unlock();
    }
    Log.log("after remove " + this);
    Log.log("*****************************");
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("[");
    for (int i = 0; i < items.length; i++) {

      if (items[i] != null) {
        sb.append(items[i] + ",");
      } else {
        sb.append(" ,");
      }
    }
    return sb.toString().replaceAll(",$", "]");
  }

  public static void main(String[] args) throws InterruptedException {
    BlockQueue<Integer> blockQueue = new BlockQueue<>(3);
    AtomicInteger i = new AtomicInteger();
    new Thread(() -> {
      try {
        while (true) {

          blockQueue.add(i.getAndIncrement());
          Thread.sleep(new Random().nextInt(2000));
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
    new Thread(() -> {
      try {
        while (true) {
          blockQueue.remove();
          Thread.sleep(new Random().nextInt(1000));
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
    Thread.sleep(100000);
  }
}
