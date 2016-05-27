package evaluator.ast;


public class ASTApply extends AST {

    private AST function;
    private AST argument;

    //add by sean
    public ASTApply() {
    	
    }
    public ASTApply(AST function, AST argument){
        this.function = function;
        this.argument = argument;
    }

    public AST getFunction(){ return function; }
    public AST getArgument(){ return argument; }
    public void setFunction(AST function){ this.function = function; }
    public void setArgument(AST argument){ this.argument = argument; }


}
