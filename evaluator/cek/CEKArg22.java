package evaluator.cek;

import evaluator.ast.Operators;

/**
 * Created by David on 025 25-May-16.
 */
public class CEKArg22 extends CEK {
    private Operators operator;
    private CEK arg;

    public CEKArg22(Operators operator, CEK arg) {
        this.operator = operator;
        this.arg = arg;
    }

    public Operators getOperator() {
        return operator;
    }

    public void setOperator(Operators operator) {
        this.operator = operator;
    }

    public CEK getArg() {
        return arg;
    }

    public void setArg(CEK arg) {
        this.arg = arg;
    }

}
