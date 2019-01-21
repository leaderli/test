package servlet;

import util.ThreadUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by li on 6/1/17.
 */
//@WebServlet("/*")
public class First extends HttpServlet {
  private Semaphore semaphore = new Semaphore(5);
  private AtomicInteger count = new AtomicInteger();
  private final BlockingDeque<String> queue = new LinkedBlockingDeque();

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PrintWriter writer = response.getWriter();
    try {
      if (semaphore.tryAcquire(2, TimeUnit.SECONDS)) {
      } else {
        System.out.println("抢不到资源 不干了");
      }
      int i;
      while (true) {
        i = count.get();
        if (count.compareAndSet(i, ++i)) {
          break;
        }
      }
      ThreadUtil.println("begin " + i + " " + semaphore.availablePermits());
      Thread.sleep(5000);
      ThreadUtil.println("end " + i + " " + semaphore.availablePermits());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    writer.write("fuck");
    try {
      queue.put(Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void init() throws ServletException {
    System.out.println("init");
    new Thread(() -> First.this.run()).start();
  }

  private void run() {
    String task;
    while (true) {
      try {
        task = queue.take();
        System.out.println("release " + task);
        semaphore.release(1);
      } catch (InterruptedException e) {
      }
    }
  }
}
