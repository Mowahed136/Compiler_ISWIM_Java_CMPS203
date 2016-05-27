package evaluator.cek;

import evaluator.ast.ASTValue;

import java.util.HashMap;

public class CEKFun extends CEK {
    ASTValue value;

    public CEKFun(ASTValue value){ this.value = value; }

    public ASTValue getValue() {
        return value;
    }

    public void setValue(ASTValue value) {
        this.value = value;
    }
}
