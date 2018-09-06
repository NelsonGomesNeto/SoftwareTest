import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class _795_NumerosHexagonaisIncompletosTest {

	private final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_795_NumerosHexagonaisIncompletos/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() { System.setOut(new PrintStream(outputStream)); }

	@Test
	void example() throws FileNotFoundException {
		String expected = "1" + ls + "6" + ls + "13" + ls + "24" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_795_NumerosHexagonaisIncompletos.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}
