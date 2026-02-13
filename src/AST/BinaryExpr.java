package AST;

import Lexer.Token;

public class BinaryExpr implements Expr {
    public final Expr left;
    public final Token operator;
    public final Expr right;

    public BinaryExpr(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return "" + left + operator +right;
    }
}

