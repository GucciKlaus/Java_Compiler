package AST.EXPR;

public class StringExpr implements Expr{
    public final String value;

    public StringExpr(String value) {
        this.value = value;
    }
}
