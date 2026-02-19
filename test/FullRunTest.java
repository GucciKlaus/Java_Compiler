import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class FullRunTest {

    @Test
    void fullRun_expectedOutput() {

        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        try {
            Main.main(new String[] {"fulltest.txt"});

        } finally {
            System.setOut(oldOut);
        }

        String out = normalize(baos.toString());


        String filtered = out.lines()
                .filter(l -> l.startsWith("IntValue{") || l.startsWith("DoubleValue{") || l.startsWith("StringValue{"))
                .reduce("", (acc, l) -> acc + l + "\n")
                .trim();

        String expected = String.join("\n",
                // ===== BASIC + INT SECTION =====
                "IntValue{value=1}",
                "IntValue{value=4}",
                "IntValue{value=4}",
                "IntValue{value=8}",
                "IntValue{value=-5}",
                "IntValue{value=2}",
                "IntValue{value=5}",
                "IntValue{value=5}",
                "IntValue{value=9}",
                "StringValue{value='ja'}",
                "StringValue{value='correct'}",
                "IntValue{value=19}",
                "IntValue{value=5}",
                "IntValue{value=40}",
                "StringValue{value='p>q outer false'}",
                "StringValue{value='q>5 inner true'}",
                "StringValue{value='grade C'}",

                // ===== DOUBLE BASICS =====
                "DoubleValue{value=2.5}",
                "DoubleValue{value=4.0}",
                "DoubleValue{value=3.75}",
                "DoubleValue{value=6.5}",
                "DoubleValue{value=6.5}",
                "DoubleValue{value=7.0}",
                "DoubleValue{value=3.0}",

                // ===== MIXED INT + DOUBLE =====
                "DoubleValue{value=3.5}",
                "DoubleValue{value=7.5}",
                "DoubleValue{value=3.5}",
                "DoubleValue{value=7.5}",
                "DoubleValue{value=7.5}",
                "DoubleValue{value=8.5}",
                "DoubleValue{value=7.5}",
                "DoubleValue{value=7.5}",
                "DoubleValue{value=1.5}",
                "DoubleValue{value=2.25}",
                "DoubleValue{value=3.75}",
                "DoubleValue{value=2.0}",

                // ===== COMPLEX DOUBLE EXPRESSIONS =====
                "DoubleValue{value=7.0}",
                "DoubleValue{value=9.0}",

                // ===== IF WITH DOUBLE =====
                "StringValue{value='d1>a false'}",
                "StringValue{value='b>d2 true'}",
                "StringValue{value='expr>3 true'}",
                "StringValue{value='score C'}",

                // ===== REASSIGNMENTS =====
                "DoubleValue{value=3.5}",
                "DoubleValue{value=4.0}",
                "IntValue{value=8}",

                // ===== FINAL MIXED =====
                "DoubleValue{value=15.0}",
                "DoubleValue{value=4.0}"
        );

        assertEquals(expected, filtered);
    }

    private static String normalize(String s) {
        return s.replace("\r\n", "\n");
    }
}
