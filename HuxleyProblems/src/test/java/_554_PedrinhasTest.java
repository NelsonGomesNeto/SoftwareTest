import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class _554_PedrinhasTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_554_Pedrinhas/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "caso 1: 1 5" + ls + "caso 2: 3 6" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_554_Pedrinhas.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}