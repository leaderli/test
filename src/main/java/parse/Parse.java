package parse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parse {

  public static char c1 = '{';
  public static char c2 = '}';
  public static char c3 = ':';
  public static char c4 = '|';
  Stack<Character> check = new Stack<>();
  Stack expression = new Stack();
  Stack temp = new Stack();
  String test = "{fuc:m1|m2}";

  public List parse(String expr) {
    System.out.println("expr " + expr);
    List<String> list = new ArrayList<>();
    char[] chars = expr.toCharArray();
    if (chars.length < 4 || chars[0] != c1 || chars[chars.length - 1] != c2) {
      throw new RuntimeException("expression is error " + expr);
    }
    String ex = "";
    for (int i = 1; i < chars.length - 1; i++) {
      if (chars[i] == c1) {
        String temp = "";
        check.push(c1);
        temp += chars[i];

        while (!check.isEmpty()) {
          i++;
          temp += chars[i];
          if (chars[i] == c1) {
            check.push(c1);
          } else if (chars[i] == c2) {
            check.pop();
          }
        }
        temp = parse(temp).toString();
        ex = ex + temp;
        continue;
      }
      if (chars[i] == c3) {

        list.add(ex);
        System.out.println(ex);
        ex = "";
        continue;
      }
      if (chars[i] == c4) {

        list.add(ex);
        System.out.println(ex);
        ex = "";
        continue;
      }
      ex += chars[i];
    }
    if (StringUtils.isNotBlank(ex)) {
      list.add(ex);
      System.out.println(ex);
    }
    System.out.println("result " + list);
    return list;
  }

  @Test
  public void test() {
    parse("{${1:}2:2|{a:b|cG}}");
    parse("{voice:0001|0002|{if:condition|go1|go2}}{a:}");
  }
}
