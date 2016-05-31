package evaluator.ast;

import evaluator.cek.CEK;

abstract public class AST extends CEK {
	
    // added by sean
	public String astType;
	public String astValue;
	public AST astLeft;
	public AST astRight;
	

}
