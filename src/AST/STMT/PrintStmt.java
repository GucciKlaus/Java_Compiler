package AST.STMT;


import AST.EXPR.Expr;

/**
 * Represents a print statement in the syntax tree (AST)
 * Eveluates an expression and outputs the result
 */
public class PrintStmt implements Stmt {
    public final Expr expression;

    public PrintStmt(Expr expression) {
        this.expression = expression;
    }

//    @Override
//    public String toString() {
//        return expression.toString();
//    }
}

