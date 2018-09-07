import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class _814_DominoTest {

	private static final String ls = "\n";//System.lineSeparator();
	private static final String basePath = "./src/test/resources/_814_Domino/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "Teste 1" + ls + "sim" + ls +	ls + "Teste 2" + ls + "nao" + ls + ls +	"Teste 3" + ls + "sim" + ls + ls + "Teste 4" + ls +	"sim" + ls + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_814_Domino.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}