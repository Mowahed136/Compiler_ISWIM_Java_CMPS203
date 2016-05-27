package front_end;


public class InputToken {
	String element;
	String tokenType;
	
	public InputToken(String s, String type) {
		element = s;
		tokenType = type;
	}
	public String getTokenType() {
		return tokenType;
	}
	public String getElement() {
		return element;
	}
	
}
