package Interpreter;

import AST.EXPR.BinaryExpr;
import AST.EXPR.Expr;
import AST.EXPR.NumberExpr;
import AST.EXPR.VariableExpr;
import AST.STMT.AssignStmt;
import AST.STMT.PrintStmt;
import AST.STMT.Stmt;
import AST.STMT.VarDeclStmt;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Executes the syntax tree step after step (AST executer)
 */
public class Interpreter {
    private final Map<String, Integer> env = new HashMap<>();

    /**
     * Goes through a list of statements and executes them
     * @param stmts
     */
    public void execute(List<Stmt> stmts) {
        for(int i = 0; i < stmts.size();i++){
            Stmt stmt = stmts.get(i);
            if (stmt instanceof PrintStmt ps) {
                Object value = evaluate(ps.expression);
                System.out.println(value);
            }else if (stmt instanceof VarDeclStmt vd){
                Object value = vd.initializer == null ? 0 : evaluate(vd.initializer);
                if(env.containsKey(vd.name)){
                    throw new RuntimeException("Variable already declared " + vd.name);
                }
                env.put(vd.name, (Integer) value);
            }else if (stmt instanceof AssignStmt as){
                if(!env.containsKey((as.name))){
                    throw new RuntimeException("Keyname not there, Variable is not defined " + as.name);
                }
                Object value = evaluate(as.value);
                env.put(as.name,(Integer) value);
                continue;
            }else{
                throw new IllegalArgumentException("Statement not available now "+ stmt.getClass().getSimpleName());
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
        }else if (expr instanceof VariableExpr ve) {
            if (!env.containsKey(ve.name)) {
                throw new RuntimeException("Undefined variable: " + ve.name);
            }
            return env.get(ve.name);
        }


        throw new RuntimeException("Unknown expression");
    }
}

