package study.pattern;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布
 */
interface Publish {
  void publish(SubscribeAndPublish subscribeAndPublish);

  void update(String msg);
}


/**
 * 订阅
 */
interface Subscribe {
  String getName();

  void subscribe();

  class SubscribeImpl implements Subscribe {
    private String name;
    private SubscribeAndPublish subscribeAndPublish;

    public SubscribeImpl(String name, SubscribeAndPublish subscribeAndPublish) {
      this.name = name;
      this.subscribeAndPublish = subscribeAndPublish;
    }

    @Override
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public SubscribeAndPublish getSubscribeAndPublish() {
      return subscribeAndPublish;
    }

    public void setSubscribeAndPublish(SubscribeAndPublish subscribeAndPublish) {
      this.subscribeAndPublish = subscribeAndPublish;
    }

    @Override
    public void subscribe() {
      this.subscribeAndPublish.SUBSCRIBE_BLOCK_QUEUE.add(this);
    }
  }
}

class PublishImpl implements Publish {
  private String name;
  private SubscribeAndPublish subscribeAndPublish;

  public PublishImpl(String name) {
    this.name = name;
  }

  @Override
  public void publish(SubscribeAndPublish subscribeAndPublish) {
    this.subscribeAndPublish = subscribeAndPublish;
  }

  @Override
  public void update(String msg) {
    System.out.println("------");
    subscribeAndPublish.SUBSCRIBE_BLOCK_QUEUE.stream().forEach(x -> {
      System.out.println(name + " " + msg + " " + x.getName());
    });
  }
}

public class SubscribeAndPublish {
  public List<Publish> PUBLISH_BLOCK_QUEUE = new ArrayList<>(100);
  public List<Subscribe> SUBSCRIBE_BLOCK_QUEUE = new ArrayList<>(100);

  public static void main(String[] args) {
    SubscribeAndPublish sa = new SubscribeAndPublish();
    Publish publish1 = new PublishImpl("发布1");
    Publish publish2 = new PublishImpl("发布2");
    publish1.publish(sa);
    publish2.publish(sa);
    Subscribe subscribe1 = new Subscribe.SubscribeImpl("订阅1", sa);
    Subscribe subscribe2 = new Subscribe.SubscribeImpl("订阅2", sa);
    subscribe1.subscribe();
    subscribe2.subscribe();
    System.out.println(sa.SUBSCRIBE_BLOCK_QUEUE.size());
    publish1.update("fff");
    publish2.update("kkkk");

  }


  @Test
  public void test2() {
    System.out.println("".matches("(\\w{2})\\1"));
  }
}

