import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    @Test
    void readAll() throws IOException {
        String expectedASCII = "LoL" + System.lineSeparator() + System.lineSeparator() + "Miau";
        String expectedUnicode = "\uD83C\uDD5B\uD83C\uDD5E\uD83C\uDD5B";
        String pathASCII = "src/test/resources/test";
        FileInputStream ASCIIstream = new Reader.MockFileInputStream(new File(pathASCII));
        String pathUnicode = "src/test/resources/unicode";
        FileInputStream UnicodeStream = new Reader.MockFileInputStream(new File(pathUnicode));
        assertAll(
//                () -> assertNull(Reader.readAll(null), "String not null for not existent file"),
                () -> assertEquals(expectedASCII, Reader.readAll(ASCIIstream), "ASCII strings are different"),
                () -> assertEquals(expectedUnicode, Reader.readAll(UnicodeStream), "Unicode strings are different")
        );
    }
}