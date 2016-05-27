package front_end;

public class ParserToken {
	String element;
	String tokenType;
	
	public ParserToken(String s, String type) {
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
