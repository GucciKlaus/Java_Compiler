package AST.EXPR;

/**
 * Represents a variable in the syntax tree (AST)
 * The parser creates a variable expression when a variable keyword appears ("int")
 * Leaf node (no child expressions)
 */
public class VariableExpr implements Expr {
    public final String name;
    public VariableExpr(String name) {this.name = name;}
}
