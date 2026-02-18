package AST.EXPR;


/**
 * Represents a boolean literal in the syntax tree (AST)
 * The parser creates a boolean literal, if a boolean is created, or is the result of something
 * Leaf node (no child expressions)
 */
public class BooleanExpr implements Expr{
    public final boolean value;

    public BooleanExpr(boolean value) {
        this.value = value;
    }
}
