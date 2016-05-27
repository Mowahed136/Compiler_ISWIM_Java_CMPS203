package evaluator.cek;

import evaluator.ast.Operators;

/**
 * Created by David on 025 25-May-16.
 */
public class CEKArg11 extends CEK {
    private Operators operator;

    public CEKArg11(Operators operator) {
        this.operator = operator;
    }

    public Operators getOperator() {
        return operator;
    }

    public void setOperator(Operators operator) {
        this.operator = operator;
    }
}
