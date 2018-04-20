package myutil;

import java.util.Stack;

/**
 * @author li
 */
public class Xpath {
	public static final char SEPARATOR = '/';

	/**
	 * 用两个栈来实现解析表达式,表达式以{@link Xpath.SEPARATOR}分割
	 */
	public static Stack<String> lexical(String expression) {
		if (expression.charAt(0) != SEPARATOR) {
			throw new RuntimeException(expression + " should start with " + SEPARATOR);
		}
		char[] chars = expression.toCharArray();
		Stack<String> operation = new Stack<>();
		Stack<Character> characters = new Stack<>();
		for (int i = chars.length - 1; i >= 0; i--) {
			if (chars[i] == SEPARATOR) {
				StringBuilder result = new StringBuilder();
				while (!characters.isEmpty()) {
					result.append(characters.pop());
				}
				operation.push(result.toString());
			} else {
				characters.push(chars[i]);
			}
		}
		return operation;
	}
	/**
	 *  支持按＊查找，＊通配任意符合条件的key
	 */
	public Object select(String expression) {
		//dev
		return null;
	}
}
