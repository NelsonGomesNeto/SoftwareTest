import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class _581_BolsaDeValoresTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_581_BolsaDeValores/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "20" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_581_BolsaDeValores.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}

	@Test
	void big() throws FileNotFoundException {
		String result = assertTimeout(Duration.ofSeconds(1), () -> {
			System.setIn(new FileInputStream(basePath + "big.in"));
			_581_BolsaDeValores.HuxleyCode.main(null);
			return(outputStream.toString());
		});

		String expected = "15621471" + ls;
		assertEquals(expected, result, "Failing example test case");
	}
}