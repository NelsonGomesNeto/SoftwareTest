import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class _576_ColorindoEmDuasCoresTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_576_ColorindoEmDuasCores/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "NOT BICOLORABLE." + ls + "BICOLORABLE." + ls + "BICOLORABLE." + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_576_ColorindoEmDuasCores.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}
