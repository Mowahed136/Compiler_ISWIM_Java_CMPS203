package evaluator.cek;

import evaluator.ast.ASTVariable;

import java.util.HashMap;

public class ContextAndEnvironment {
    public CEK context;
    public Environment environment;

    public ContextAndEnvironment(CEK context) {
        this(context, new Environment());
    }

    public ContextAndEnvironment(CEK context, Environment environment) {
        this.environment = environment;
        this.context = context;
    }
}
