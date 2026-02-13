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

            // Lexer aufrufen
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.disassembleString(source);

            // Tokens ausgeben
            for (Token t : tokens) {
                System.out.println(t);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
