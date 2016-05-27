package front_end;
import evaluator.ast.*;
import java.util.Stack;
import java.util.LinkedList;
public class Parser {
	public AST parse(LinkedList<ParserToken> list) {
		AST node = null;
		if (list.size() == 0) {
			// the length the expression is wrong
			throw new IllegalArgumentException();
		}
		
		ParserToken firstItem = list.get(0);
		
		// check the first item in the expression
		if (firstItem.getTokenType() == "num") {
			 node = new ASTNumber(Integer.valueOf(firstItem.getElement()));
			 node.astValue = firstItem.getElement();
			 return node;
		} else if (firstItem.getTokenType() == "var") {
			node = new ASTVariable(firstItem.getElement());
			node.astValue = firstItem.getElement();
			return node;
		} else if (firstItem.getTokenType() == "lp") {
			// remove the ( of expression
			list.remove(0);
			// check if the expression end with )
			if (list.size() > 0 && list.get(list.size() - 1).getTokenType() == "rp")
			{
				list.remove(list.size() - 1);
				// check the expression start with oP1 or oP2 or appor lam
				if (list.size() > 0 && 
						(list.get(0).getTokenType() == "op1" || 
						 list.get(0).getTokenType() == "op2" ||
						 list.get(0).getTokenType() == "lam" ||
						 list.get(0).getTokenType() == "app" )) {
					if (list.get(0).getTokenType() == "op1") {
						// generate the node 
						node = new ASTOp1(list.get(0).getElement());
						node.astValue = list.get(0).getElement();
						list.remove(0);
						// add the child node
						AST childNode = parse(list);
						((ASTOp1) node).setArg1(childNode);
						node.astLeft = childNode;
					} else if (list.get(0).getTokenType() == "op2") {
							// generate the node
							node = new ASTOp2(list.get(0).getElement());
							node.astValue = list.get(0).getElement();
							list.remove(0);
							
							// find the first expression argument
							if (list.size() > 0) {
								LinkedList<ParserToken> leftList = new LinkedList<ParserToken>();
								if (list.get(0).getTokenType() == "num" || list.get(0).getTokenType() == "var") {
									// set the first argument expression
									leftList.add(list.get(0));
									AST childLeftNode = parse(leftList);
									((ASTOp2)node).setArg1(childLeftNode);
									node.astLeft = childLeftNode;
									// set the second argument expression
									list.remove(0);
									AST childRightNode = parse(list);
									((ASTOp2)node).setArg1(childRightNode);
									node.astRight = childRightNode;
								} else if (list.get(0).getTokenType() == "lp") {
									Stack<String> stack = new Stack<String>();
									stack.push(list.get(0).getTokenType());
									int index = 0;
									int listLength = list.size();
									while (!stack.isEmpty()) {
										index++;
										if (index >= listLength) {
											// can't find matched ')'even the index larger than listLength
											throw new IllegalArgumentException();
										} else {
											if (list.get(index).getTokenType() == "lp") {
												stack.push(list.get(0).getTokenType());
											} else if (list.get(index).getTokenType() == "rp") {
												stack.pop();
											}
										}
									}
									// add the first expression in ( ) to the leftList
									while (index >= 0) {
										index--;
										leftList.add(list.get(0));
										list.remove(0);
									}
									// set the leftChild node
									AST childLeftNode = parse(leftList);
									((ASTOp2)node).setArg1(childLeftNode);
									node.astLeft = childLeftNode;
									// set the rightChild node
									AST childRightNode = parse(list);
									((ASTOp2)node).setArg1(childRightNode);
									node.astRight = childRightNode;
								}
							} else {
								// the length of expression is wrong
								throw new IllegalArgumentException();
							}
					} else if (list.get(0).getTokenType() == "lam") {
							// generate the node
							node = new ASTLambda();	
							node.astValue = list.get(0).getElement();
							list.remove(0);
							
							// find the first var argument 
							if (list.size() > 0 && list.get(0).getTokenType() == "var") {
								LinkedList<ParserToken> leftList = new LinkedList<ParserToken>();
							
									// set the first argument expression
									leftList.add(list.get(0));
									AST childLeftNode = parse(leftList);
									((ASTLambda)node).setBoundVariable((ASTVariable)childLeftNode);
									node.astLeft = childLeftNode;
									// set the second argument expression
									list.remove(0);
									AST childRightNode = parse(list);
									((ASTLambda)node).setBody(childRightNode);
									node.astRight = childRightNode;
							
							} else {
								// the length of expression is wrong or the first argument is not var
								throw new IllegalArgumentException();
							}
							
						} else if (list.get(0).getTokenType() == "app") {
							// generate the node
							node = new ASTApply();
							node.astValue = list.get(0).getElement();
							list.remove(0);
							
							// find the first expression argument
							if (list.size() > 0) {
								LinkedList<ParserToken> leftList = new LinkedList<ParserToken>();
								if (list.get(0).getTokenType() == "num" || list.get(0).getTokenType() == "var") {
									// set the first argument expression
									leftList.add(list.get(0));
									AST childLeftNode = parse(leftList);
									((ASTApply)node).setFunction(childLeftNode);
									node.astLeft = childLeftNode;
									// set the second argument expression
									list.remove(0);
									AST childRightNode = parse(list);
									((ASTApply)node).setArgument(childRightNode);
									node.astRight = childRightNode;
								} else if (list.get(0).getTokenType() == "lp") {
									Stack<String> stack = new Stack<String>();
									stack.push(list.get(0).getTokenType());
									int index = 0;
									int listLength = list.size();
									while (!stack.isEmpty()) {
										index++;
										if (index >= listLength) {
											// can't find matched ')'even the index larger than listLength
											throw new IllegalArgumentException();
										} else {
											if (list.get(index).getTokenType() == "lp") {
												stack.push(list.get(0).getTokenType());
											} else if (list.get(index).getTokenType() == "rp") {
												stack.pop();
											}
										}
									}
									// add the first expression in ( ) to the leftList
									while (index >= 0) {
										index--;
										leftList.add(list.get(0));
										list.remove(0);
									}
									// set the leftChild node
									AST childLeftNode = parse(leftList);
									((ASTApply)node).setFunction(childLeftNode);
									node.astLeft = childLeftNode;
									// set the rightChild node
									AST childRightNode = parse(list);
									((ASTApply)node).setArgument(childRightNode);
									node.astRight = childRightNode;
								}
							} else {
								// the length of expression is wrong
								throw new IllegalArgumentException();
							}							
							
							
						
						} 

				} else {
					// first item in the expression don't belong to op1 or op2 or lam or app
					throw new IllegalArgumentException();
				}
			} else {
				// expression start with '(' don't end with ')'
				throw new IllegalArgumentException();
			}
		} else {
			//  expression don't start with num or var or '('
			throw new IllegalArgumentException();
		}
		
		return node;
	}
	
	

	
}
