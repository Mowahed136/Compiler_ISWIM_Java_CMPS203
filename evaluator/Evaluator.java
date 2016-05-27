package evaluator;

import evaluator.ast.*;
import evaluator.cek.*;

import java.util.Stack;

public class Evaluator {
    private Stack<ContextAndEnvironment> continuation = new Stack<ContextAndEnvironment>();

    public Evaluator(){}

    /**
     * Public entry point for evaluating an AST Tree
     * @param context
     * @return Returns an ASTValue (either an ASTNumber or ASTLambda expression)
     * @throws HaltError If the system halts in non valid state
     */
    public ASTValue evaluate(AST context) throws HaltError {
        ContextAndEnvironment ce = new ContextAndEnvironment(context);

        //
        // EVALUATION LOOP
        //
        while( ! ((ce.context instanceof ASTValue) && continuation.empty()) ){
            if     ( ce.context instanceof ASTApply )                                                      { ce = eval_cek1(ce); }
            else if( ce.context instanceof ASTValue  && continuation.peek().context instanceof CEKFun)     { ce = eval_cek3(ce); }
            else if( ce.context instanceof ASTValue  && continuation.peek().context instanceof CEKArg)     { ce = eval_cek4(ce); }
            else if( ce.context instanceof ASTVariable )                                                   { ce = eval_cek7(ce); }
            else if( ce.context instanceof ASTOp1 )                                                        { ce = eval_cek2a(ce); }
            else if( ce.context instanceof ASTOp2 )                                                        { ce = eval_cek2b(ce); }
            else if( ce.context instanceof ASTNumber && continuation.peek().context instanceof CEKArg11 )  { ce = eval_cek5a(ce); }
            else if( ce.context instanceof ASTValue  && continuation.peek().context instanceof CEKArg11 )  { /*ce = eval_cek5ap(ce);*/ throw new HaltError(); } // Unedefined situation, see Piazza question.
            else if( ce.context instanceof ASTNumber && continuation.peek().context instanceof CEKArg22 )  { ce = eval_cek5b(ce); }
            else if( ce.context instanceof ASTValue  && continuation.peek().context instanceof CEKArg22 )  { /*ce = eval_cek5bp(ce);*/ throw new HaltError(); }
            else if( ce.context instanceof ASTValue  && continuation.peek().context instanceof CEKArg12 )  { ce = eval_cek6b(ce); }
            else if( ce.context == null ) throw new HaltError();
        } //end-while

        // Verify result and return
        if( ! (ce.context instanceof ASTValue) ) throw new HaltError();
        else return (ASTValue)ce.context;
    }

    //
    // EVALUATION IMPLEMENTATIONS
    //
    private ContextAndEnvironment eval_cek1(ContextAndEnvironment ce){
        ASTApply app = (ASTApply)ce.context;
        continuation.push(new ContextAndEnvironment(new CEKArg(app.getArgument()), ce.environment));    // Push argument on stack
        return new ContextAndEnvironment(app.getFunction(), ce.environment);                            // Return function as context
    }

    private ContextAndEnvironment eval_cek3(ContextAndEnvironment ce){
        // Question: what do you do if V = X in [cek3]?
        ASTValue v = (ASTValue)ce.context;
        Environment e = ce.environment;
        ContextAndEnvironment cont = continuation.pop();
        CEKFun fun = (CEKFun)cont.context;
        Environment contenv = cont.environment;
        ASTLambda lam = (ASTLambda)fun.getValue();

        contenv.put(lam.getBoundVariable(), ce.context);                                    // Add the value term and current context to the environment
        return new ContextAndEnvironment(lam.getBody(), contenv);                           // Return the body of the lambda expression and the environment from the stack
    }

    private ContextAndEnvironment eval_cek4(ContextAndEnvironment ce){
        ASTValue v = (ASTValue)ce.context;
        ContextAndEnvironment cont = continuation.pop();                                    // Pull the continuation from the stack
        CEKArg cekarg = (CEKArg)cont.context;
        AST n = (AST)cekarg.getClosure();
        continuation.push( new ContextAndEnvironment(new CEKFun(v), ce.environment) );      // Push a function with the original value term and environment
        return new ContextAndEnvironment(n ,cont.environment);                              // Return the value and environment from the stack
    }

    private ContextAndEnvironment eval_cek7(ContextAndEnvironment ce){
        ASTVariable x = (ASTVariable)ce.context;
        return new ContextAndEnvironment(ce.environment.get(x), new Environment());
    }

    private ContextAndEnvironment eval_cek2a(ContextAndEnvironment ce){
        ASTOp1 op1 = (ASTOp1)ce.context;
        Operators o = op1.getOperator();
        CEK m = op1.getArg1();

        continuation.push(new ContextAndEnvironment(new CEKArg11(o)));
        return new ContextAndEnvironment(m, ce.environment);
    }

    private ContextAndEnvironment eval_cek2b(ContextAndEnvironment ce){                     // Slides seem wrong on this one, they don't consider N
        ASTOp2 op2 = (ASTOp2)ce.context;
        Operators o = op2.getOperator();
        CEK m = op2.getArg1();
        CEK n = op2.getArg2();

        continuation.push(new ContextAndEnvironment(new CEKArg12(o,n)));
        return new ContextAndEnvironment(m,ce.environment);
    }

    private ContextAndEnvironment eval_cek5a(ContextAndEnvironment ce){
        ASTNumber b = (ASTNumber)ce.context;
        ContextAndEnvironment cont = continuation.pop();
        CEKArg11 arg11 = (CEKArg11)cont.context;
        Operators o = arg11.getOperator();

        return new ContextAndEnvironment(deltaeval1(o,b));
    }

//    private ContextAndEnvironment eval_cek5ap(ContextAndEnvironment ce){
//        //TODO waiting on question
//        return null;
//    }

    private ContextAndEnvironment eval_cek5b(ContextAndEnvironment ce){
        ASTNumber b = (ASTNumber)ce.context;
        ContextAndEnvironment cont = continuation.pop();
        CEKArg22 arg22 = (CEKArg22)cont.context;
        Operators o = arg22.getOperator();
        ASTNumber b1 = (ASTNumber)arg22.getArg();
        ASTNumber delta = (ASTNumber)deltaeval2(o, b1, b);

        return new ContextAndEnvironment(delta, new Environment());
    }

//    private ContextAndEnvironment eval_cek5bp(ContextAndEnvironment ce){
//        //TODO waiting on question
//        return null;
//    }

    private ContextAndEnvironment eval_cek6b(ContextAndEnvironment ce){
        ASTValue v = (ASTValue)ce.context;
        ContextAndEnvironment cont = continuation.pop();
        CEKArg12 arg12 = (CEKArg12)cont.context;
        CEK n = arg12.getArg();

        continuation.push(new ContextAndEnvironment(new CEKArg22(arg12.getOperator(), v),ce.environment));
        return new ContextAndEnvironment(n, cont.environment);
    }


    private ASTValue deltaeval1(Operators o, ASTNumber b){
        switch(o) {
            case ADD1:
                return new ASTNumber(b.getNumber() + 1);
            case SUB1:
                return new ASTNumber(b.getNumber() - 1);
            case ISZERO:
                return new ASTNumber( (b.getNumber().equals(0))?1:0 );
            default:
                throw new Error("Code Bug");
        }
    }

    private ASTValue deltaeval2(Operators o, ASTNumber b1, ASTNumber b2){
        switch(o) {
            case PLUS:
                return new ASTNumber(b1.getNumber() + b2.getNumber());
            case MINUS:
                return new ASTNumber(b1.getNumber() - b2.getNumber());
            case MULTIPLY:
                return new ASTNumber(b1.getNumber() * b2.getNumber());
            case EXPONENTIATE:
                return new ASTNumber((int)Math.pow(b1.getNumber() , b2.getNumber()));
            default:
                throw new Error("Code Bug");
        }
    }

}
