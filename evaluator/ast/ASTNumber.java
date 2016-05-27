package evaluator.ast;


public class ASTNumber extends ASTValue {

    private Integer number;

    public ASTNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ASTNumber) return this.number.equals(((ASTNumber) obj).getNumber());
        else return false;
    }

    @Override
    public String toString() {
        return "ASTNumber{" +
                "number=" + number +
                '}';
    }
}
