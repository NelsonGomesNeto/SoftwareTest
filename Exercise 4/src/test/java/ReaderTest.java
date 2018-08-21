import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    @Test
    void readAll() {
        String expectedASCII = "LoL" + System.lineSeparator() + System.lineSeparator() + "Miau";
        String expectedUnicode = "\uD83C\uDD5B\uD83C\uDD5E\uD83C\uDD5B";
        String pathASCII = "src/test/resources/test";
        String pathUnicode = "src/test/resources/unicode";
        assertAll(
                () -> assertNull(Reader.readAll("not existent"), "String not null for not existent file"),
                () -> assertEquals(expectedASCII, Reader.readAll(pathASCII), "ASCII strings are different"),
                () -> assertEquals(expectedUnicode, Reader.readAll(pathUnicode), "Unicode strings are different")
        );
    }
}