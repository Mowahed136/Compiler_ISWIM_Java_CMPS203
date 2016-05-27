package evaluator.ast;


public class ASTOp1 extends AST {

    private Operators operator;
    private AST arg1;

    // added by sean
    public ASTOp1(String op1) {
    	switch (op1) {
    	case "add1": this.operator = Operators.ADD1;
    		break;
    	case "sub1": this.operator = Operators.SUB1;
		break;
    	case "iszero": this.operator = Operators.ISZERO;
		break;
    	}
    }
    
    public ASTOp1(Operators operator, AST arg1) {
        this.arg1 = arg1;
        this.operator = operator;
    }

    public AST getArg1() {
        return arg1;
    }

    public void setArg1(AST arg1) {
        this.arg1 = arg1;
    }

    public Operators getOperator() {
        return operator;
    }

    public void setOperator(Operators operator) {
        this.operator = operator;
    }
}
