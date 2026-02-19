package Lexer;

/**
 * Different token types, which are used to specify characters
 */
public enum TokenType {
    LPAREN, RPAREN,LBRACE,RBRACE, // (,),{,}
    TRUE,FALSE, //true, false
    INT,STRING,NUMBER,IF,ELSE,STRING_LITERAL,DOUBLE, //int,string,number,if,else
    PLUS,MINUS, MUL, DIV, // +,-,*,/
    PRINT,IDENTIFIER, ASSIGN, //print, "a", =
    EQUAL_EQUAL,NOT_EQUAL,SMALLER,BIGGER,ESMALLER,EBIGGER,BANG, //==,!=,ze<,>,<=, >=,!
    SEMICOLON,//;
    EOF // Endoffile
}
