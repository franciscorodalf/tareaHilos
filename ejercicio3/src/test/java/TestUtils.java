import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

final class TestUtils {
    private TestUtils() {
    }

    static String captureOutput(Runnable runnable) {
        PrintStream original = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        try (PrintStream temp = new PrintStream(outContent)) {
            System.setOut(temp);
            runnable.run();
        } finally {
            System.setOut(original);
        }
        return outContent.toString();
    }
}
