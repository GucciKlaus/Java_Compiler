package Lexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Operates over the given code and builds token out of it
 */
public class Lexer {
    private String data;
    private int runner;
    private final List<Token> tokens = new ArrayList<>();

    /**
     * Takes a string and returns the tokens
     * @param data the string which is being processed
     * @return a list of tokens
     */
    public List<Token> disassembleString(String data) {
        this.data = data;
        this.runner = 0;
        tokens.clear();

        while (!isAtEnd()) {
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    /**
     * Processes a char and checks if the value is a sepcial Tokentype a digit or a letter
     */
    private void scanToken() {
        char c = peek();

        switch (c) {
            case '(' -> { tokens.add(new Token(TokenType.LPAREN, "(")); move_Forward(); }
            case ')' -> { tokens.add(new Token(TokenType.RPAREN, ")")); move_Forward(); }
            case '+' -> { tokens.add(new Token(TokenType.PLUS, "+")); move_Forward(); }
            case '-' -> { tokens.add(new Token(TokenType.MINUS, "-")); move_Forward(); }
            case ';' -> { tokens.add(new Token(TokenType.SEMICOLON, ";")); move_Forward(); }

            case ' ', '\t', '\r', '\n' -> move_Forward(); // whitespace skip

            default -> {
                if (Character.isDigit(c)) {
                    run_num();
                } else if (Character.isLetter(c) || c == '_') {
                    run_string();
                }else if (isOperator(c)){
                    check_op();
                }
            else {
                    throw new IllegalArgumentException("Not supported char: '" + c + "'");
                }
            }
        }
    }

    /**
     * Goes through a whole word and decides if the value is an int, string or a print
     */
    private void run_string() {
        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && (Character.isLetterOrDigit(peek()) || peek() == '_')) {
            sb.append(peek());
            move_Forward();
        }

        String word = sb.toString();

        TokenType type = switch (word) {
            case "int" -> TokenType.INT;
            case "string" -> TokenType.STRING;
            case "print" -> TokenType.PRINT;
            default -> TokenType.IDENTIFIER;
        };

        tokens.add(new Token(type, word));
    }

    /**
     * Goes through a number till the content isn't a digit anymore and add the whole number to the token list
     */
    private void run_num() {
        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && Character.isDigit(peek())) {
            sb.append(peek());
            move_Forward();
        }

        tokens.add(new Token(TokenType.NUMBER, sb.toString()));
    }

    /**
     * Checks which operator we have and adds the specific token
     */
    private void check_op() {
        char c = peek();
        move_Forward();

        if (!isAtEnd() && peek() == '=') {
            move_Forward();

            switch (c) {
                case '=' -> tokens.add(new Token(TokenType.EQUAL_EQUAL, "=="));
                case '>' -> tokens.add(new Token(TokenType.EBIGGER, ">="));
                case '<' -> tokens.add(new Token(TokenType.ESMALLER, "<="));
                case '!' -> tokens.add(new Token(TokenType.NOT_EQUAL, "!="));
                default -> throw new IllegalArgumentException("Unsupported operator: " + c);
            }

        } else {
            switch (c) {
                case '=' -> tokens.add(new Token(TokenType.EQUAL, "="));
                case '>' -> tokens.add(new Token(TokenType.BIGGER, ">"));
                case '<' -> tokens.add(new Token(TokenType.SMALLER, "<"));
                case '!' -> tokens.add(new Token(TokenType.BANG, "!"));
                case '/' -> tokens.add(new Token(TokenType.DIV, "/"));
                case '*' -> tokens.add(new Token(TokenType.MUL,"*"));
                default -> throw new IllegalArgumentException("Unsupported operator: " + c);
            }
        }
    }

    /**
     * Checks if the character is a operator
     */
    private boolean isOperator(char c){
        switch(c){
            case '=', '+','-','!', '<', '>', '/','*':return true;
            default:return false;
        }
    }

    /**
     * Returns the current value of the string
     */
    private char peek() {
        return data.charAt(runner);
    }

    /**
     * Sets the runner value to +1, so we can move forward in the string
     */
    private void move_Forward() {
        runner++;
    }

    /**
     * Checks if the data string is at the end or not
     */
    private boolean isAtEnd() {
        return runner >= data.length();
    }
}
