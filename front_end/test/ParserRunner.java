package front_end.test;
import front_end.InputToken;
import front_end.Parser;
import front_end.ParserToken;
import front_end.Scanner;
import front_end.Screener;

import java.util.*;

import evaluator.ast.*;

public class ParserRunner {
	
	// the function to print the node of tree
	public static void printNode(String x, int h) {
		for (int i = 0; i < h; i++) {
			System.out.print("      ");
		}
		System.out.println("[" + x + "]");
	}
	private static void showR(AST t, int h) {
		if (t == null) {
			
			return;
		}
		showR(t.astRight, h + 1);
		printNode(t.astValue, h);
		showR(t.astLeft, h + 1);
	}
	
	// main function
	public static void main(String[] args) {
		// the input string
		//String input = "";
		String input = "(app(lam x2 (add1 (+ x1 (* x2 (sub1 199)))))(+ 5 3))";
		// output the input token
		Scanner scanner = new Scanner();
		scanner.scan(input);
		System.out.println(scanner);
		
		// output the parser token
		Screener screener = new Screener(scanner);
		screener.screen();
		System.out.println(screener);
		
		// generate the parser tree
		Parser parser = new Parser();
		LinkedList<ParserToken> list = new LinkedList<ParserToken>(screener.getParserTokenList());
		AST root = parser.parse(list);
		showR(root, 0);
	
	}
	
	

}
