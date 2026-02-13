package Lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String data;
    private int runner;
    private final List<Token> tokens = new ArrayList<>();

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

    private void scanToken() {
        char c = look();

        switch (c) {
            case '(' -> { tokens.add(new Token(TokenType.LBRACKET, "(")); move_Forward(); }
            case ')' -> { tokens.add(new Token(TokenType.RBRACKET, ")")); move_Forward(); }
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

    private void run_string() {
        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && (Character.isLetterOrDigit(look()) || look() == '_')) {
            sb.append(look());
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

    private void run_num() {
        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && Character.isDigit(look())) {
            sb.append(look());
            move_Forward();
        }

        tokens.add(new Token(TokenType.NUMBER, sb.toString()));
    }

    private void check_op() {
        char c = look();
        move_Forward(); // erstes Zeichen konsumieren

        if (!isAtEnd() && look() == '=') {
            move_Forward(); // zweites Zeichen konsumieren

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
                default -> throw new IllegalArgumentException("Unsupported operator: " + c);
            }
        }
    }


    private boolean isOperator(char c){
        switch(c){
            case '=', '+','-','!', '<', '>':return true;
            default:return false;
        }
    }

    private char look() {
        return data.charAt(runner);
    }


    private void move_Forward() {
        runner++;
    }

    private boolean isAtEnd() {
        return runner >= data.length();
    }
}
