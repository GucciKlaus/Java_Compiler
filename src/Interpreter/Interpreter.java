package Interpreter;

import AST.EXPR.*;
import AST.STMT.*;
import Interpreter.Types.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Executes the syntax tree step after step (AST executer)
 */
public class Interpreter {
    private final Map<String, Value> env = new HashMap<>();

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
                Value value = vd.initializer == null ? null : evaluate(vd.initializer);
                if(env.containsKey(vd.name)){
                    throw new RuntimeException("Variable already declared " + vd.name);
                }
                env.put(vd.name, value);
            }else if (stmt instanceof AssignStmt as){
                if(!env.containsKey((as.name))){
                    throw new RuntimeException("Keyname not there, Variable is not defined " + as.name);
                }
                Value value = evaluate(as.value);
                env.put(as.name,value);
                continue;
            }else if(stmt instanceof IfStmt is){
                Value condition = evaluate(is.condition);
                if(condition instanceof  BoolValue){
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
     * @return an Object -> the specific Datatype
     */
    private Value evaluate(Expr expr) {
        if (expr instanceof NumberExpr ne) {
            return ne.isInt?(new IntValue((int)ne.value)):new DoubleValue(ne.value);
        }


        if (expr instanceof StringExpr s) {
            return new StringValue(s.value);
        }

        if (expr instanceof BooleanExpr b) {
            return new BoolValue(b.value);
        }

        if (expr instanceof VariableExpr ve) {
            Value v = env.get(ve.name);
            if (v == null) {
                throw new RuntimeException("Undefined variable: " + ve.name);
            }
            return v;
        }

        if (expr instanceof BinaryExpr be) {
            Value left = evaluate(be.left);
            Value right = evaluate(be.right);

            return switch (be.operator.type) {
                case PLUS -> {
                    if (isString(left) && isString(right)) {
                        yield new StringValue(asString(left) + asString(right));
                    }
                    yield numericBinary(left, right, (a, b) -> a + b, (a, b) -> a + b);
                }
                case MINUS -> numericBinary(left, right, (a, b) -> a - b, (a, b) -> a - b);
                case MUL -> numericBinary(left, right, (a, b) -> a * b, (a, b) -> a * b);
                case DIV -> numericBinary(left, right, (a, b) -> a / b, (a, b) -> a / b);
                case BIGGER -> numericCompare(left, right, (a, b) -> a > b, (a, b) -> a > b);
                case SMALLER -> numericCompare(left, right, (a, b) -> a < b, (a, b) -> a < b);
                case EBIGGER -> numericCompare(left, right, (a, b) -> a >= b, (a, b) -> a >= b);
                case ESMALLER -> numericCompare(left, right, (a, b) -> a <= b, (a, b) -> a <= b);
                case EQUAL_EQUAL ->
                        numericCompare(left, right, (a, b) -> Objects.equals(a, b), (a, b) -> Objects.equals(a, b));
                case NOT_EQUAL ->
                        numericCompare(left, right, (a, b) -> !Objects.equals(a, b), (a, b) -> !Objects.equals(a, b));
                default -> throw new RuntimeException("Unknown expression" + expr.toString());
            };

        }
        return null;
    }

    private Value numericBinary(Value left, Value right, java.util.function.IntBinaryOperator intOp, java.util.function.DoubleBinaryOperator doubleOp) {
        if (!isNumber(left) || !isNumber(right)) {
            throw new RuntimeException("Type error: expected numbers, got " + left.getClass().getSimpleName() + " and " + right.getClass().getSimpleName());
        }

        boolean anyDouble = isDouble(left) || isDouble(right);
        if (anyDouble) {
            return new DoubleValue(doubleOp.applyAsDouble(asDouble(left), asDouble(right)));
        }
        return new IntValue(intOp.applyAsInt(asInt(left), asInt(right)));
    }

    private Value numericCompare(Value left, Value right, java.util.function.BiPredicate<Double, Double>dd, java.util.function.BiPredicate<Integer,Integer>ii){
        if(!isNumber(left) || !isNumber(right)){
            throw new RuntimeException("Type error: expected numbers, got " + left.getClass().getSimpleName() + " and " + right.getClass().getSimpleName());
        }

        boolean anyDouble = isDouble(left) || isDouble(right);
        if (anyDouble) {
            return new BoolValue(dd.test(asDouble(left),asDouble(right)));
        }
        return new BoolValue(ii.test(asInt(left), asInt(right)));
    }


    private boolean isInt(Value v)    { return v instanceof IntValue; }
    private boolean isDouble(Value v) { return v instanceof DoubleValue; }
    private boolean isNumber(Value v) { return isInt(v) || isDouble(v); }
    private boolean isString(Value v) { return  v instanceof  StringValue;}

    private int asInt(Value v) {
        if (v instanceof IntValue i) return i.value;
        throw typeError("int", v);
    }

    private String asString(Value v){
        if(v instanceof StringValue s) return s.value;
        throw typeError("string",v);
    }

    private double asDouble(Value v) {
        if (v instanceof DoubleValue d) return d.value;
        if (v instanceof IntValue i) return i.value; // promotion
        throw typeError("number", v);
    }

    private RuntimeException typeError(String expected, Value got) {
        return new RuntimeException("Type error: expected " + expected + ", got " + got.getClass().getSimpleName());
    }

}

