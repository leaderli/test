package study.stack;


import li.annotation.NotNull;
import li.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author li
 */
public class StackUtil {
    private interface execution {
        int execute(int a, int b);
    }

    private static Map<String, execution> executions = new HashMap<>();

    static {
        executions.put("*", (a, b) -> a * b);
        executions.put("/", (a, b) -> a / b);
        executions.put("-", (a, b) -> a - b);
        executions.put("+", (a, b) -> a + b);
    }

    public static boolean checkBrackets(@NotNull String s) {
        //符号以中线对称出现，则小于中线的即为左符号，并且左右符号相加等于长度减一即表示对称
        String rex = "{[()]}";
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            int index = rex.indexOf(c);
            if (index < 0) {
                continue;
            }

            if (index < (rex.length() / 2)) {
                stack.push(c);
            } else {
                char pop = stack.pop();
                if (index + rex.indexOf(pop) != rex.length() - 1) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Log.log(math("1+1*1/1-1*1"));
        Log.log(math("1+1-1*1-1/1"));
        Log.log(math("1+(1-1)*1-1/1"));
    }

    public static int math(String s) {
        if (checkBrackets(s)) {
            Expression expression = new Expression();

            for (char c : s.toCharArray()) {
                expression.execute(c);
            }
            while (!expression.operation.isEmpty()) {
                expression.expression.push(expression.operation.pop() + "");
            }
            Log.log("math=" + s + " expression=" + expression.expression);
            Stack<Integer> integers = new Stack<>();
            for (String c : expression.expression) {
                if (c.matches("\\d*")) {
                    integers.push(Integer.valueOf(c));
                } else {
                    int b = integers.pop();
                    int a = integers.pop();
                    integers.push(executions.get(c).execute(a, b));
                }
            }
            return integers.pop();
        }
        throw new RuntimeException(s + " is not correct expression");
    }

    private static class Expression {
        Stack<String> expression = new Stack<>();
        Stack<Character> operation = new Stack<>();

        private Map<Character, Integer> LEVEL = new HashMap<>();

        {

            LEVEL.put('(', -2);
            LEVEL.put(')', -1);
            LEVEL.put('0', 0);
            LEVEL.put('1', 0);
            LEVEL.put('2', 0);
            LEVEL.put('3', 0);
            LEVEL.put('4', 0);
            LEVEL.put('5', 0);
            LEVEL.put('6', 0);
            LEVEL.put('7', 0);
            LEVEL.put('8', 0);
            LEVEL.put('9', 0);
            LEVEL.put('+', 1);
            LEVEL.put('-', 1);
            LEVEL.put('*', 2);
            LEVEL.put('/', 2);
        }


        private int lastCharacter = -2;

        private void execute(Character c) {
            int level = LEVEL.get(c);
            switch (level) {
                //左括号直接压入
                case -2:
                    operation.push(c);
                    break;
                case -1:
                    char pop;
                    while ((pop = operation.pop()) != '(') {
                        expression.push(pop + "");
                    }
                    break;
                case 0:
                    if (lastCharacter == 0) {
                        expression.push(expression.pop() + c);
                    } else {
                        expression.push(c + "");
                    }
                    break;
                case 1:
                case 2:
                    char peek;

                    while (!operation.isEmpty()) {
                        peek = operation.peek();
                        if (LEVEL.get(peek) < level) {
                            break;
                        }
                        expression.push(operation.pop() + "");
                    }
                    operation.push(c);

                    break;
                default:
                    break;
            }
            lastCharacter = level;
        }
    }
}
