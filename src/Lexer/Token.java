package Lexer;

public class Token {
    public TokenType type;
    public String content;


    public Token(TokenType type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Type: " + type + " Content " + content;
    }

}
