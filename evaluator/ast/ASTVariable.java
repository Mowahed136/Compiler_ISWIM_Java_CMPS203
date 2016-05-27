package evaluator.ast;

public class ASTVariable extends AST {

    private String variable;

    public ASTVariable(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public int hashCode() {
        return this.variable.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ASTVariable) return this.variable.equals(((ASTVariable) obj).getVariable());
        else return false;
    }
}
