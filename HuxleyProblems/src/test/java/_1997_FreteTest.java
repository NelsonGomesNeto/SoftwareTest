import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class _1997_FreteTest {

	private final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_1997_Frete/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() { System.setOut(new PrintStream(outputStream)); }

	@Test
	void examples() throws FileNotFoundException {
		String expected1 = "18" + ls;
		System.setIn(new FileInputStream(basePath + "example1.in"));
		outputStream.reset();
		_1997_Frete.HuxleyCode.main(null);
		String out1 = outputStream.toString();
		String expected2 = "7" + ls;
		System.setIn(new FileInputStream(basePath + "example2.in"));
		outputStream.reset();
		_1997_Frete.HuxleyCode.main(null);
		String out2 = outputStream.toString();

		assertAll("Failing example test cases",
			() -> assertEquals(InOutReader.uniformString(expected1), InOutReader.uniformString(out1), "Failed test case 1"),
			() -> assertEquals(InOutReader.uniformString(expected2), InOutReader.uniformString(out2), "Failed test case 2"));
	}
}
