package AST.EXPR;

import Lexer.Token;
/**
 * Represents a binary expression node in the abstract syntax tree (AST)
 * Content: Left operand, operator and right operand
 * Representation created by the parser
 */
public class BinaryExpr implements Expr {
    public final Expr left;
    public final Token operator;
    public final Expr right;

    public BinaryExpr(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

//    @Override
//    public String toString() {
//        return "" + left + operator +right;
//    }
}

