package evaluator.test;

import evaluator.Evaluator;
import evaluator.HaltError;
import evaluator.ast.*;

public class TestEvaluator {

    public static void main(String [] args) throws HaltError {
        Evaluator evaluator = new Evaluator();
        ASTValue result = evaluator.evaluate(getMockAST());
        System.out.println(result);
    }

    /**
     * Returns an AST tree for the ISWIM expression: (lambda x.x)(1)
     * For testing purposes.
     * @return
     */
    public static AST getMockAST(){
        return new ASTApply(
                new ASTLambda(new ASTVariable("x"), new ASTVariable("x")),
                new ASTNumber(1)
        );
    }

}
