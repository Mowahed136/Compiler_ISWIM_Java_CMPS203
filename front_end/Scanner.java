package front_end;
import front_end.InputToken;
import java.util.*;

public class Scanner {
	public ArrayList<InputToken> inputList = new ArrayList<InputToken>();
	public void scan(String input) {
		int len = input.length();
		
		for (int i = 0; i < len; i++) {
			if (input.charAt(i) == '(') {
				inputList.add(new InputToken("(", "Lparen"));
			} else if (input.charAt(i) == ')') {
				inputList.add(new InputToken(")", "Rparen"));
			} else if (Character.isDigit(input.charAt(i))) {
				int j = i+1;
				while (j < len && Character.isDigit(input.charAt(j))) {
					j++;
				}
				inputList.add(new InputToken(input.substring(i, j),"NUM"));
				i = j - 1;
			} else if (Character.isLetter(input.charAt(i))) {
				int j = i + 1;
				while (j < len && Character.isLetterOrDigit(input.charAt(j))) {
					j++;
				}
				inputList.add(new InputToken(input.substring(i, j),"ID"));
				i = j - 1;
			} else if (input.charAt(i) == '+') {
				inputList.add(new InputToken("+", "OP"));
			} else if (input.charAt(i) == '-') {
				inputList.add(new InputToken("-", "OP"));
			} else if (input.charAt(i) == '*') {
				inputList.add(new InputToken("*", "OP"));
			} else if (input.charAt(i) == '^') {
				inputList.add(new InputToken("^", "OP"));
			} else if (input.charAt(i) == ' ' || input.charAt(i) == '\n') {
				int j = i + 1;
				while (j < len && (input.charAt(j) == ' ' || input.charAt(j) == '\n')) {
					j++;
				}
				inputList.add(new InputToken("","WS"));
				i = j - 1;
			}
		}
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (InputToken i : inputList) {
			sb.append(i.getTokenType());
			sb.append(":");
			sb.append(i.getElement());
			sb.append('\n');
		}
		return sb.toString();
	}
}
