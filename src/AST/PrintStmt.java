package AST;

public class PrintStmt implements Stmt {
    public final Expr expression;

    public PrintStmt(Expr expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}

