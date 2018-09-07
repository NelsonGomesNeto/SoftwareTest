import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class _241_OndeEstaOMarmoreTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_241_OndeEstaOMarmore/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "CASE# 1:" + ls + "5 found at 4" + ls + "CASE# 2:" + ls + "2 not found" + ls + "3 found at 3" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_241_OndeEstaOMarmore.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}