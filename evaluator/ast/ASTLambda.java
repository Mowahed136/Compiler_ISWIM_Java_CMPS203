package evaluator.ast;


public class ASTLambda extends ASTValue {

    private ASTVariable boundVariable;
    private AST body;

    // add by sean
    public ASTLambda() {
    	
    }
    
    public ASTLambda(ASTVariable boundVariable, AST body) {
        this.boundVariable = boundVariable;
        this.body = body;
    }

    public ASTVariable getBoundVariable() {
        return boundVariable;
    }

    public void setBoundVariable(ASTVariable boundVariable) {
        this.boundVariable = boundVariable;
    }

    public AST getBody() {
        return body;
    }

    public void setBody(AST body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ASTLambda{" +
                "boundVariable=" + boundVariable +
                ", body=" + body +
                '}';
    }
}
