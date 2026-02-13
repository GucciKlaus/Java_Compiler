import AST.Stmt;
import Interpreter.Interpreter;
import Lexer.Lexer;
import Lexer.Token;
import Parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Starting processing file...");

            // Datei komplett als String laden
            String source = Files.readString(Path.of("src/test.txt"));

            // Lexer.Lexer aufrufen
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.disassembleString(source);

            // Tokens ausgeben
            for (Token t : tokens) {
                System.out.println(t);
            }

            Parser parser = new Parser(tokens);
            List<Stmt> stmts = parser.parse();

            for(int i = 0; i < stmts.size(); i ++){
             System.out.println(stmts.get(i));
            }

            Interpreter interpreter = new Interpreter();
            interpreter.execute(stmts);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
