package parse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static parse.IVRParse.State.*;


public class IVRParse {
  /**
   * :
   */
  public static char C_COLON = ':';
  /**
   * {
   */
  public static char C_LEFT = '{';
  /**
   * }
   */
  public static char C_RIGHT = '}';
  /**
   * |
   */
  public static char C_VERTICAl = '|';
//  static Map<String, String> local = new HashMap<>();
//  static Map<String, Order> order = new HashMap<>();

//  static {
//    order.put("log", arr -> {
//      System.out.println(Arrays.toString(arr));
//      return "";
//    });
//  }

  /**
   * 顺序执行，执行单元为{}
   * 若碰到嵌套执行单元，则先计算嵌套单元
   * 嵌套单元的返回值必须是String
   * 使用状态机去实现
   */
  public static String execute(String expression) {
    State state = State.INIT;
    String func = "";
    List<String> params = new ArrayList<>();
    StringBuilder sb = null;
    State before_inner_state = INNER_INIT;
    StringBuilder before_inner_sb = null;
    int stack = 0;
    StringBuilder inner_expression = null;
    char[] chars = expression.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      System.out.println("" + expression + "_____   state_" + state + " ------->" + c);
      switch (state) {
        case INIT:
          if (c == C_LEFT) {
            state = BEGIN;
            sb = new StringBuilder();
          } else {
            throw new RuntimeException("expression must start with {");
          }
          break;
        case BEGIN:
          if (c == C_COLON) {
            state = COLON;
          } else {
            sb.append(c);
            state = FUNC;
          }
          break;
        case FUNC:
          if (c == C_COLON) {
            state = COLON;
          } else if (c == C_LEFT) {
            before_inner_state = state;
            state = INNER_BEGIN;
            inner_expression = new StringBuilder();
            inner_expression.append(c);
            stack++;
            before_inner_sb = sb;
            System.out.println("func_hashcode=" + sb.hashCode() + "; func_state" + before_inner_state);
          } else {
            sb.append(c);
          }
          break;
        case COLON:
          func = sb.toString();
          System.out.println("expression=" + expression + ";colon " + func + " sb.hashcode=" + sb.hashCode());
          if (c == C_RIGHT) {
            params.add("");
            state = END;
          } else if (c == C_VERTICAl) {
            state = VERTICAL;
            sb = new StringBuilder();
          } else {
            sb = new StringBuilder();
            sb.append(c);
            System.out.println(state + "==>" + sb);
            state = PARAM;
          }
          break;
        case PARAM:
          if (c == C_VERTICAl) {
            state = VERTICAL;
          } else if (c == C_RIGHT) {
            params.add(sb.toString());
            System.out.println(sb.toString());
            state = END;
          } else if (c == C_LEFT) {
            before_inner_state = state;
            state = INNER_BEGIN;
            inner_expression = new StringBuilder();
            stack++;
            inner_expression.append(c);
            before_inner_sb = sb;
          } else {
            sb.append(c);
          }
          break;
        case VERTICAL:
          params.add(sb.toString());
          if (c == C_RIGHT) {
            params.add("");
            state = END;
          } else {
            sb = new StringBuilder();
            sb.append(c);
            state = PARAM;
          }
          break;
        case END:
          break;
        /**
         * 内部嵌套只需要找到匹配的右括号即可
         * 可能包含嵌套表达式的状态只可能是
         * {@link FUNC}
         * {@link PARAM}
         */
        case INNER_BEGIN:
          if (c == C_LEFT) {
            stack++;
          } else if (c == C_RIGHT) {
            stack--;
          }
          inner_expression.append(c);
          if (stack == 0) {
            state = INNER_END;
          }

          break;
        case INNER_END:
          if (before_inner_state == INNER_INIT) {
            throw new RuntimeException(" must store last state");
          }
          System.out.println("inner_expression=" + inner_expression);
          state = before_inner_state;
          before_inner_sb.append(execute(inner_expression.toString()));
          System.out.println("inner_hashcode=" + before_inner_sb.hashCode() + "; inner_state" + before_inner_state);
          i--;
          break;
        default:
          break;
      }
    }
    StringBuilder result = new StringBuilder();
    result.append("{expr=" + expression + "[func=" + func + ";praram_size=" + params.size() + ";params=");
    for (int i = 0; i < params.size(); i++) {
      result.append(params.get(i) + "|");
    }
    return (result.toString()).replaceAll("\\|$", "") + "]}";


  }

  @Test
  public void test() {
//    System.out.println(execute("{f:}"));
//    System.out.println(execute("{f:|}"));
//    System.out.println(execute("{f:|1}"));
//    System.out.println(execute("{f:2}"));
//    System.out.println(execute("{f:3|}"));
//    System.out.println(execute("{f:4|5}"));
    System.out.println(execute("{f{g:}:}"));
//    System.out.println(execute("{f{g:}:4{p:}|5}"));

  }

  /**
   *
   */
  static enum State {
    /**
     * 初始状态
     */
    INIT,
    /**
     * 第一个{
     */
    BEGIN,
    /**
     * 最后一个}
     */
    END,
    /**
     * 方法位
     */
    FUNC,
    /**
     * 参数位
     */
    PARAM,
    /**
     * 冒号位
     */
    COLON,
    /**
     * 竖线
     */
    VERTICAL,
    /**
     * 内部初始位
     */
    INNER_INIT,
    /**
     * 内部｛
     */
    INNER_BEGIN,
    /**
     * 内部 }
     */
    INNER_END,

    INNER_FUNC,
    /**
     * 内部参数为
     */
    INNER_PARAM,
    /**
     * 内部冒号位
     */
    INNER_COLON,
  }

  interface Order {
    String run(String[] arr);

  }
}
