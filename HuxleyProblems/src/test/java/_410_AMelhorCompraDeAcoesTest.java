import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class _410_AMelhorCompraDeAcoesTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_410_AMelhorCompraDeAcoes/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "43" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_410_AMelhorCompraDeAcoes.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}