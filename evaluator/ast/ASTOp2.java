package evaluator.ast;

public class ASTOp2 extends AST {

    private Operators operator;
    private AST arg1;
    private AST arg2;
    
    // added by sean
    public ASTOp2(String op2) {
    	switch (op2) {
    	case "+": this.operator = Operators.PLUS;
    		break;
    	case "-": this.operator = Operators.MINUS;
		break;
    	case "*": this.operator = Operators.MULTIPLY;
		break;
    	case "^": this.operator = Operators.EXPONENTIATE;
		break;
    	}
    }
    public ASTOp2(Operators operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public Operators getOperator() {
        return operator;
    }

    public void setOperator(Operators operator) {
        this.operator = operator;
    }

    public AST getArg1() {
        return arg1;
    }

    public void setArg1(AST arg1) {
        this.arg1 = arg1;
    }

    public AST getArg2() {
        return arg2;
    }

    public void setArg2(AST arg2) {
        this.arg2 = arg2;
    }
}
