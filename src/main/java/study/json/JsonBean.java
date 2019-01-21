package study.json;

public class JsonBean {
  private int test;
  private FuckBean fuck;
  private String name;

  public int getTest() {
    return test;
  }

  public void setTest(int test) {
    this.test = test;
  }

  public FuckBean getFuck() {
    return fuck;
  }

  public void setFuck(FuckBean fuck) {
    this.fuck = fuck;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "JsonBean{" +
        "test=" + test +
        ", fuck=" + fuck +
        ", name='" + name + '\'' +
        '}';
  }
}
