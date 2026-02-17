package Parser;

import AST.EXPR.BinaryExpr;
import AST.EXPR.Expr;
import AST.EXPR.NumberExpr;
import AST.EXPR.VariableExpr;
import AST.STMT.AssignStmt;
import AST.STMT.PrintStmt;
import AST.STMT.Stmt;
import AST.STMT.VarDeclStmt;
import Lexer.Token;
import Lexer.TokenType;
import java.util.ArrayList;
import java.util.List;

/**
 * Iterates over the token list and starts building the syntax tree
 */
public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Creates a new List of statements and checks if the token list is not finished yet
     * @return the list of statements
     */
    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(statement());
        }
        return statements;
    }

    /**
     * Checks if the actual token is a statement or not, if so it will be added to the list
     * @return the statement
     */
    private Stmt statement(){
        if (match(TokenType.PRINT)){
            return printStatement();
        }else if(match(TokenType.INT)) {
             return varDeclStatement();
        }else if(check(TokenType.IDENTIFIER) && checkNext(TokenType.ASSIGN))
        {
          return assignStatement();
        }else{
                throw new RuntimeException("Expected statement, got " + peek().type);
        }
    }

    /**
     * Expects a printStatement
     * Content: print(expression);
     * @return a printStatement
     */
    private Stmt printStatement() {
        consume(TokenType.LPAREN, "Expected '(' after print");

        Expr expr = expression();

        consume(TokenType.RPAREN, "Expected ')'");
        consume(TokenType.SEMICOLON, "Expected ';' after statement");
        return new PrintStmt(expr);

    }

    /**
     * Decleration of a variable
     * @return a stmt to declare a variable
     */
    private Stmt varDeclStatement() {
        Token nameTok = consume(TokenType.IDENTIFIER, "Expected variable name after 'int'");
        Expr init = null;

        if (match(TokenType.ASSIGN)) {
            init = expression();
        }

        consume(TokenType.SEMICOLON, "Expected ';' after variable declaration");
        return new VarDeclStmt(nameTok.content, init);
    }

    /**
     * Assign a value to a declared variable
     * @return the assign stmt
     */
    private Stmt assignStatement() {
        Token nameTok = consume(TokenType.IDENTIFIER, "Expected variable name");
        consume(TokenType.ASSIGN, "Expected '=' after variable name");

        Expr value = expression();
        consume(TokenType.SEMICOLON, "Expected ';' after assignment");

        return new AssignStmt(nameTok.content, value);
    }



    /**
     * Manages + and -, with left priority, calls term, to check for higher level operators
     * @return a full Expression or the left part
     */
    private Expr expression() {
        Expr left = term();

        while (match(TokenType.PLUS) || match(TokenType.MINUS)) {
            Token operator = previous();
            Expr right = term();
            left = new BinaryExpr(left, operator, right);
        }

        return left;
    }

    /**
     * Manages the higher level operators * and /
     * @return a full expression or the left part
     */
    private Expr term() {
        Expr left = primary();

        while (match(TokenType.MUL) || match(TokenType.DIV)) {
            Token operator = previous();
            Expr right = primary();
            left = new BinaryExpr(left, operator, right);
        }

        return left;
    }

    /**
     * Checks which content comes next, return the number expression or a normal expression
     */
    private Expr primary() {
        if (match(TokenType.NUMBER)) return new NumberExpr(Integer.parseInt(previous().content));

        if (match(TokenType.IDENTIFIER)) return new VariableExpr(previous().content);

        if (match(TokenType.LPAREN)) {
            Expr expr = expression();
            consume(TokenType.RPAREN, "Expected ')' after expression");
            return expr;
        }

        throw new RuntimeException("Expected expression, got: " + peek().type);
    }



    /**
     * Checks if the current token matches the give type
     * If it matches the parser consumes the token + 1 and returns true;
     * If we have no match just false will be returned
     * @param type the type where we are at
     * @return if we are at the end or not
     */
    private boolean match(TokenType type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    /**
     * When we are not at the end we move further, and return the previous content
     * @param type, where we are at
     * @param message the error message
     * @return the token
     */
    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new RuntimeException(message + " (got " + peek().type + ")");
    }

    /**
     * Checks if this is the last type or not
     * @param type, Tokentype
     * @return boolean
     */
    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    /**
     * Checks if the next token is our specific type
     * @param type the actual type
     * @return boolean
     */
    private boolean checkNext(TokenType type) {
            if (current + 1 >= tokens.size()) return false;
            return tokens.get(current + 1).type == type;
        }


    /**
     * Move +1 previous
     * @return previous token
     */
    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    /**
     * Check if we have reached the end
     * @return boolean
     */
    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    /**
     * Returns the current token
     * @return Token
     */
    private Token peek() {
        return tokens.get(current);
    }

    /**
     * Returns the previous token
     * Do not call if you are at the first element, not handled
     * @return Token
     */
    private Token previous() {
        return tokens.get(current - 1);
    }
}
