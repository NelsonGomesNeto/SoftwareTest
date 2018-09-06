import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class _200_DentistaTest {

	private final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_200_Dentista/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() { System.setOut(new PrintStream(outputStream)); }

	@Test
	void example() throws FileNotFoundException {
		String expected = "2" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_200_Dentista.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}
