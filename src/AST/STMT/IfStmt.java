package AST.STMT;

import AST.EXPR.Expr;

public class IfStmt implements Stmt {
    public final Expr condition;
    public final BlockStmt thenBranch;
    public final BlockStmt elseBranch;

    public IfStmt(Expr condition, BlockStmt thenBranch, BlockStmt elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
}
