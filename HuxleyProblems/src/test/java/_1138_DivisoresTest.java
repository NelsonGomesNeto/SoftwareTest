import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class _1138_DivisoresTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_1138_Divisores/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "6" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_1138_Divisores.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}

	@Test
	void noSolution() throws FileNotFoundException {
		String expected = "-1" + ls;
		System.setIn(new FileInputStream(basePath + "noSolution.in"));
		_1138_Divisores.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing noSolution test case");
	}
}