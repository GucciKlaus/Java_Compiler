package Interpreter;

import AST.*;
import Lexer.TokenType;

import java.util.List;

public class Interpreter {

    public void execute(List<Stmt> stmts) {
        for(int i = 0; i < stmts.size();i++){
            Stmt stmt = stmts.get(i);
            if (stmt instanceof PrintStmt ps) {
                Object value = evaluate(ps.expression);
                System.out.println(value);
            }
        }
    }

    private Object evaluate(Expr expr) {
        if (expr instanceof NumberExpr ne) {
            return ne.value;
        }

        if (expr instanceof BinaryExpr be) {
            Object left = evaluate(be.left);
            Object right = evaluate(be.right);

            if (be.operator.type == TokenType.PLUS) {
                return (int)left + (int)right;
            }
        }

        throw new RuntimeException("Unknown expression");
    }
}

