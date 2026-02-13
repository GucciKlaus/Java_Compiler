package Parser;

import AST.BinaryExpr;
import AST.Expr;
import AST.NumberExpr;
import AST.PrintStmt;
import AST.Stmt;
import Lexer.Token;
import Lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(statement());
        }
        return statements;
    }

    private Stmt statement(){
        if (check(TokenType.PRINT)){
            return printStatement();
        }else{
            throw new RuntimeException("Exepcted statement, got " + peek().type);
        }
    }

    private Stmt printStatement() {
        consume(TokenType.PRINT, "Expected 'print'");
        consume(TokenType.LBRACKET, "Expected '(' after print");

        Expr expr = expression();

        consume(TokenType.RBRACKET, "Expected ')'");
        consume(TokenType.SEMICOLON, "Expected ';' after statement");

        return new PrintStmt(expr);
    }

    private Expr expression() {
        Expr left = primary();

        while (match(TokenType.PLUS)) { // erlaubt 1+2+3
            Token operator = previous();
            Expr right = primary();
            left = new BinaryExpr(left, operator, right);
        }

        return left;
    }

    private Expr primary() {
        if (match(TokenType.NUMBER)) {
            return new NumberExpr(Integer.parseInt(previous().content));
        }

        throw new RuntimeException("Expected expression, got: " + peek().type);
    }

    private boolean match(TokenType type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new RuntimeException(message + " (got " + peek().type + ")");
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}
