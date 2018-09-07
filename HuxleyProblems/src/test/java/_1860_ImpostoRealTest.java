import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class _1860_ImpostoRealTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_1860_ImpostoReal/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example1() throws FileNotFoundException {
		String expected = "10" + ls;
		System.setIn(new FileInputStream(basePath + "example1.in"));
		_1860_ImpostoReal.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}

	@Test
	void example2() throws FileNotFoundException {
		String expected = "44" + ls;
		System.setIn(new FileInputStream(basePath + "example2.in"));
		_1860_ImpostoReal.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}

	@Test
	void big() throws FileNotFoundException {
		String expected = "102416336" + ls;
		System.setIn(new FileInputStream(basePath + "big.in"));
		_1860_ImpostoReal.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}