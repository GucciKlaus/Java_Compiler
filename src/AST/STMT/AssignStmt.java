package AST.STMT;

import AST.EXPR.Expr;
/**
 * Represents a variable assign statement in the syntax tree (AST)
 */
public class AssignStmt implements Stmt{
    public final String name;
    public final Expr value;

    public AssignStmt(String name, Expr value) {
        this.name = name;
        this.value = value;
    }
}
