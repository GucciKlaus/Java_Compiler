public class Token {
    TokenType type;
    String content;


    public Token(TokenType type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Type: " + type + " Content " + content;
    }

}
