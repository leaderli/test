package myutil;

import java.util.Stack;

/**
 * @author li
 */
public class Xpath {
	private static final char SEPARATOR = '/';

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
}
