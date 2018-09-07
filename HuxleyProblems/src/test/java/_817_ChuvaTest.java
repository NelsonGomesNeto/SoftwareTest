import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class _817_ChuvaTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_817_Chuva/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "12" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_817_Chuva.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}