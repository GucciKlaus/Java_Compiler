package AST.EXPR;

/**
 * Represents a numeric literal in the syntax tree (AST)
 * The parser creates a number expression when a number token is appearing in the source code ("42")
 * Leaf node (no child expressions)
 */
public class NumberExpr implements Expr {
    public final double value;
    public final boolean isInt;

    public NumberExpr(double value,boolean isInt) {
        this.value = value;
        this.isInt = isInt;
    }

    @Override
    public String toString() {
        return  "" +value;
    }
}

