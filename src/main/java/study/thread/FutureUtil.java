package study.thread;

import li.util.Log;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class FutureUtil {
  private static int run() {
    try {
      for (int i = 0; i < 10; i++) {
        Thread.sleep(100);
        Log.log(i);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return 1;
  }

  @Test
  public void test() throws InterruptedException, ExecutionException {
    ExecutorService service = Executors.newSingleThreadExecutor();
    Future<Integer> submit = service.submit(() -> {
      Thread.sleep(5000);
      return 1;
    });
    Log.log("Begin future");
    Thread.sleep(2000);
    try {
      System.out.println(submit.get(4, TimeUnit.SECONDS));
      System.out.println(submit.get(4, TimeUnit.SECONDS));
      System.out.println(submit.get(4, TimeUnit.SECONDS));
      System.out.println(submit.get(4, TimeUnit.SECONDS));
    } catch (TimeoutException e) {
      Log.log("time out ");
      Log.log();
    }
  }

  @Test
  public void test2() throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<Integer> submit = executorService.submit(FutureUtil::run);

    Log.log(submit.get());
  }
}
