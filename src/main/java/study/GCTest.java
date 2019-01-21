package study;

import bean.User;
import li.util.Log;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GCTest {
  public static void main(String[] args) throws InterruptedException {

    Thread.sleep(20000);
    List list = new ArrayList();
    for (int i = 0; i < 20; i++) {
      list.add(new Byte[1000 * 1024]);
      Thread.sleep(1000);
    }
  }

  @Test
  public void test() {
    Map<String, String> map = new HashMap<>();
    map.put("1", "1");
    map.put("2", "2");
    Object[] objects = map.entrySet().stream().map(e -> {

      e.setValue(e.getValue() + "0");


      return e;
    }).toArray();
    System.out.println(map);
  }

  @org.junit.Test
  public void test1() throws ScriptException {
    User user = new User();
    user.setUsername("123");
    user.setPassword("456");
    Map<String, String> map = new HashMap<>();
    map.put("a1", "1");
    map.put("a2", "2");
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine scriptEngine = sem.getEngineByExtension("js");
    scriptEngine.put("user", user);
    scriptEngine.put("map", map);
    Object result = scriptEngine.eval("map.a1");
    System.out.println(result);
    result = scriptEngine.eval("map['a1']");
    System.out.println(result);
  }

  @Test
  public void test2() {
    Map map = new HashMap();
    map.put("k", "1");
    int a = get(map, "k", 0);
    System.out.println(a);
  }

  private <T> T get(Map map, String key, T def) {
    Log.log(def.getClass());
    Class c = def.getClass();
    try {

      return (T) c.cast(map.get(key));
    } catch (Throwable t) {
      Log.logline();
      t.printStackTrace();
    }
    return def;
  }

  private <T> T cast(Object value) {
    return (T) value;
  }

  private void st(int s) {
    if (s == 1) {
      Log.log(1);
    } else if (s == 2) {

      Log.log(2);
    } else {
      Log.log(3);
    }
  }

}
