import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class _151_SimCityTest {

	private final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_151_SimCity/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() { System.setOut(new PrintStream(outputStream)); }

	@Test
	void example() throws FileNotFoundException {
		String expected = "3.41" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_151_SimCity.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}

	@Test
	void multiple() throws FileNotFoundException {
		String expected = "3.41" + ls + "3.00" + ls + "7.07" + ls + "12.73" + ls + "27.08" + ls;
		System.setIn(new FileInputStream(basePath + "multiple.in"));
		_151_SimCity.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing multiple test case");
	}
}
