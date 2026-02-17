package AST.STMT;

import AST.EXPR.Expr;

/**
 * Represents a variable declaration statement in the syntax tree (AST)
 */
public class VarDeclStmt implements Stmt{
    public final String name;
    public final Expr initializer;

    public VarDeclStmt(String name, Expr initializer) {
        this.name = name;
        this.initializer = initializer;
    }
}
