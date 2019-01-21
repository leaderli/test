package study.json;

import java.util.List;

public class FuckBean {
  private int f1;
  private List<Integer> f2;

  public int getF1() {
    return f1;
  }

  public void setF1(int f1) {
    this.f1 = f1;
  }

  public List<Integer> getF2() {
    return f2;
  }

  public void setF2(List<Integer> f2) {
    this.f2 = f2;
  }

  @Override
  public String toString() {
    return "FuckBean{" +
        "f1=" + f1 +
        ", f2=" + f2 +
        '}';
  }
}
