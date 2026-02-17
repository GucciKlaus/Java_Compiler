package Interpreter;

import AST.*;
import Lexer.TokenType;

import java.util.List;

/**
 * Executes the syntax tree step after step (AST executer)
 */
public class Interpreter {

    /**
     * Goes trough a list of statements and executes them
     * @param stmts
     */
    public void execute(List<Stmt> stmts) {
        for(int i = 0; i < stmts.size();i++){
            Stmt stmt = stmts.get(i);
            if (stmt instanceof PrintStmt ps) {
                Object value = evaluate(ps.expression);
                System.out.println(value);
            }
        }
    }

    /**
     * Eveluates an expression
     * @param expr the expression which should be eveluated
     * @return an Object -> which will be later transformed to Value, if we want to add further datatypes
     */
    private Object evaluate(Expr expr) {
        if (expr instanceof NumberExpr ne) {
            return ne.value;
        }

        if (expr instanceof BinaryExpr be) {
            Object left = evaluate(be.left);
            Object right = evaluate(be.right);

            switch(be.operator.type){
                case PLUS -> {
                    return (int)left + (int)right;
                }
                case MINUS -> {
                    return (int)left - (int) right;
                }
                case MUL -> {
                    return (int) left* (int)right;
                }

                case DIV -> {
                    return (int) left / (int) right;
                }
            }
        }

        throw new RuntimeException("Unknown expression");
    }
}

