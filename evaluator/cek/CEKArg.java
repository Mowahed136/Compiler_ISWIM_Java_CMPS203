package evaluator.cek;

/**
 * Created by David on 025 25-May-16.
 */
public class CEKArg extends CEK {
    private CEK closure;

    public CEKArg(CEK closure) {
        this.closure = closure;
    }

    public CEK getClosure() {
        return closure;
    }

    public void setClosure(CEK closure) {
        this.closure = closure;
    }
}
