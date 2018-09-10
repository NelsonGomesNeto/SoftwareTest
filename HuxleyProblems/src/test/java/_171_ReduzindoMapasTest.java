import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

public class _171_ReduzindoMapasTest {

	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeAll
	static void setupAll() {
		System.setOut(new PrintStream(outputStream));
	}

	@BeforeEach
	void resetOutput() {
		outputStream.reset();
	}

	@Test
	void testPriorityQueue() {
		String expected = "3" + System.lineSeparator() + "2" + System.lineSeparator();
		_171_ReduzindoMapas.HuxleyCode.testingPriorityQueue();
		assertEquals(expected, outputStream.toString(), "Priority Queue isn't working as expected");
	}

	@Test
	void example() throws FileNotFoundException {
		System.setIn(new FileInputStream("./src/test/resources/_171_ReduzindoMapas/example.in"));
		String expected = "34" + System.lineSeparator();
		_171_ReduzindoMapas.HuxleyCode.main(null);
		assertEquals(InOutReader.uniformString(expected), InOutReader.uniformString(outputStream.toString()), "Failing example test case");
	}

	@Test
	void big() throws FileNotFoundException {
		System.setIn(new FileInputStream("./src/test/resources/_171_ReduzindoMapas/big.in"));
		String expected = "1441" + System.lineSeparator();
		_171_ReduzindoMapas.HuxleyCode.main(null);
		assertEquals(InOutReader.uniformString(expected), InOutReader.uniformString(outputStream.toString()), "Failing big test case");
	}
}
