import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

public class HelloWorldTest {

    private PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setupStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testHello() {
        Hello hello = new Hello();
        assertNotNull(hello, "Hello is null");

        assertAll("text1 and text2 aren't both null",
                () -> assertNull(getField("text1", hello), "text1 isn't null"),
                () -> assertNull(getField("text2", hello), "text2 isn't null"));

        String expected = "Hello World!" + System.lineSeparator();
        hello.sayHello();

        assertAll("text1 and text2 aren't both null",
                () -> assertEquals("Hello", getField("text1", hello), "text1 isn't null"),
                () -> assertEquals("World!", getField("text2", hello), "text2 isn't null"));

        assertEquals(expected, outContent.toString(), "Didn't output \"Hello World!\"");
    }

    private String getField(String name, Hello obj) {
        try {
            Field field = Hello.class.getDeclaredField(name);
            field.setAccessible(true);

            return((String) field.get(obj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
