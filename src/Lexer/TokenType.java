package Lexer;

/**
 * Different token types, which are used to specify characters
 */
public enum TokenType {
    LPAREN, RPAREN,
    INT,STRING,NUMBER,
    PLUS,MINUS, MUL, DIV,
    PRINT,IDENTIFIER, ASSIGN,
    EQUAL_EQUAL,NOT_EQUAL,NOT,SMALLER,BIGGER,ESMALLER,EBIGGER,BANG,
    SEMICOLON,
    EOF
}
