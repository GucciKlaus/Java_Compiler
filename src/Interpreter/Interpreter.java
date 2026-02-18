package Interpreter;

import AST.EXPR.*;
import AST.STMT.*;


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
            }else if(stmt instanceof IfStmt is){
                Object condition = evaluate(is.condition);
                if((boolean) condition){
                    execute(is.thenBranch.statements);
                }else if (is.elseBranch!=null){
                    execute(is.elseBranch.statements);
                }
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
                    if (left instanceof Integer && right instanceof Integer)
                        return (int) left + (int) right;
                    if (left instanceof String || right instanceof String)
                        return left.toString() + right.toString();
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
                case BIGGER-> {
                    return (int) left > (int) right;
                }
                case SMALLER-> {
                    return (int) left < (int) right;
                }

                case EBIGGER-> {
                    return (int) left >= (int) right;
                }
                case ESMALLER-> {
                    return (int) left <= (int) right;
                }
                case EQUAL_EQUAL-> {
                    return left.equals(right);
                }
                case NOT_EQUAL -> {
                    return !left.equals(right);
                }
            }
        }else if (expr instanceof VariableExpr ve) {
            if (!env.containsKey(ve.name)) {
                throw new RuntimeException("Undefined variable: " + ve.name);
            }
            return env.get(ve.name);
        }else if (expr instanceof StringExpr s) {
            return s.value;
        }else if (expr instanceof BooleanExpr b) {
            return b.value;
        }



        throw new RuntimeException("Unknown expression" + expr.toString());
    }
}

