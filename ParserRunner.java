//package front_end.test;
//import com.sun.deploy.util.StringUtils;
import evaluator.Evaluator;
import evaluator.HaltError;
import front_end.InputToken;
import front_end.Parser;
import front_end.ParserToken;
import front_end.Scanner;
import front_end.Screener;
import java.io.*;
import java.util.*;

import evaluator.ast.*;

// This class include the main class, responsible for run the test


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
	public static void main(String[] args) throws HaltError {
		// the input string
		//String input = "";
		//String input = "(lam hello (app hello hello))";
		//String input = "(^ (-0 2) (-0 5))";
		//String input = args[0]


		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = "";
			while (input != null) {
				input = br.readLine();
				if (input == null) {
					break;
				}
				// the main loop code here

				System.out.println("Input string:");
				System.out.println("  "+input);

				// output the input token
				Scanner scanner = new Scanner();
				scanner.scan(input);
				System.out.println("Scanner tokens:");
				System.out.println(scanner);
				// output the parser token

				Screener screener = new Screener(scanner);
				screener.screen();
				System.out.println("Parser tokens:");
				System.out.println(screener);

				// generate the parser tree
				Parser parser = new Parser();
				LinkedList<ParserToken> list = new LinkedList<ParserToken>(screener.getParserTokenList());
				AST root = parser.parse(list);
				System.out.println("Syntax tree:");
				System.out.println("  "+parser.printTree(root));

				// generate the evaluation answer

				ASTValue result = null;
				try {
					result = (new Evaluator()).evaluate(root);
				}catch(HaltError e){
					System.out.print("Answer:\n  Stuck");
					return;
				}
				System.out.println("Answer:");

				if(result instanceof ASTNumber)
					System.out.println("  "+((ASTNumber)result).getNumber());
				else if(result instanceof ASTLambda)
					System.out.println("  function");
				else System.out.println("  Stuck");

				System.out.print("\n");

			}
				System.out.println("done");

		}catch (Exception ex) {
			ex.printStackTrace();
		}


/*
;
		System.out.println("Input string:");
		System.out.println("  "+input);

		// output the input token
		Scanner scanner = new Scanner();
		scanner.scan(input);
		System.out.println("Scanner tokens:");
		System.out.println(scanner);
		// output the parser token

		Screener screener = new Screener(scanner);
		screener.screen();
		System.out.println("Parser tokens:");
		System.out.println(screener);

		// generate the parser tree
		Parser parser = new Parser();
		LinkedList<ParserToken> list = new LinkedList<ParserToken>(screener.getParserTokenList());
		AST root = parser.parse(list);
		System.out.println("Syntax tree:");
		System.out.print(parser.printTree(root));

*/

		//showR(root, 0);
/*
		// evaluate the parsed tree
		ASTValue result = (new Evaluator()).evaluate(root);
		System.out.println(result);
*/

	}



}
