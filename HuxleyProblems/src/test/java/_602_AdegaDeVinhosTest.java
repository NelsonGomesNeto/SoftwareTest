import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class _602_AdegaDeVinhosTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_602_AdegaDeVinhos/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "50" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_602_AdegaDeVinhos.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}

	@Test
	void big() {
		String result = assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			System.setIn(new FileInputStream(basePath + "big.in"));
			_602_AdegaDeVinhos.HuxleyCode.main(null);
			return(outputStream.toString());
		});

		String expected = "1089299" + ls;
		assertEquals(expected, result, "Failing big test case");
	}
}