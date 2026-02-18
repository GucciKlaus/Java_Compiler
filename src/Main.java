import AST.STMT.Stmt;
import Interpreter.Interpreter;
import Lexer.Lexer;
import Lexer.Token;
import Parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
Heart of the Project, reads a file, compiles it and starts it
 */
public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Starting processing file...");

            String source = Files.readString(Path.of("src/test.txt"));

            // Lexer calling
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.disassembleString(source);

            // Token printer
            for (Token t : tokens) {
                System.out.println(t);
            }

            Parser parser = new Parser(tokens);
            //Parser starting
            List<Stmt> stmts = parser.parse();
            for (Stmt p : stmts) {
                System.out.println(p);
            }

            Interpreter interpreter = new Interpreter();
            //Interpreter starting
            interpreter.execute(stmts);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
