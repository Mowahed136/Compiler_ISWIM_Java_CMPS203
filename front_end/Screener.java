package front_end;
import java.util.ArrayList;
import java.util.LinkedList;
import front_end.Scanner;
import front_end.ParserToken;

public class Screener {
	public ArrayList<InputToken> inputList = new ArrayList<InputToken>();
	public LinkedList<ParserToken> outputList = new LinkedList<ParserToken>();
	
	public Screener(Scanner s) {
		inputList = s.inputList;
	}
	
	public LinkedList<ParserToken> getParserTokenList() {
		return outputList;
	}
	
	public void screen() {
		for (int i = 0; i < inputList.size(); i++) {
			InputToken item = inputList.get(i);
			if (item.getTokenType() == "ID") {
				switch (item.getElement()) {
				case "lam": outputList.add(new ParserToken("lam","lam"));
							break;
				case "app": outputList.add(new ParserToken("app","app"));
							break;
				case "add1": outputList.add(new ParserToken("add1","op1"));
							break;
				case "sub1": outputList.add(new ParserToken("sub1","op1"));
				break;
				case "iszero": outputList.add(new ParserToken("iszero","op1"));
				break;
				default: outputList.add(new ParserToken(item.getElement(),"var"));
				}
			} else if (item.getTokenType() == "NUM") {
				outputList.add(new ParserToken(item.getElement(),"num"));
			} else if (item.getTokenType() == "OP") {
				outputList.add(new ParserToken(item.getElement(),"op2"));
			} else if (item.getTokenType() == "Lparen") {
				outputList.add(new ParserToken(item.getElement(),"lp"));
			} else if (item.getTokenType() == "Rparen") {
				outputList.add(new ParserToken(item.getElement(),"rp"));
			}
		}
	}
	

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (ParserToken o : outputList) {
			if (o.getTokenType() == "lp") {
				sb.append("Lparen");
			} else if (o.getTokenType() == "rp") {
				sb.append("Rparen");
			} else if (o.getTokenType() == "lam" || o.getTokenType() == "app"){
				sb.append(o.getTokenType());
			} else {
				sb.append(o.getTokenType());
				sb.append("(");
				sb.append(o.getElement());
				sb.append(")");
			}
			sb.append(",");
			sb.append(" ");
		}
		return sb.substring(0, sb.length() - 2);
	}
}
