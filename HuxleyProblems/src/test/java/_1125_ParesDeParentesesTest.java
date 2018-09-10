import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class _1125_ParesDeParentesesTest {

	private static final String basePath = "./src/test/resources/_1125_ParesDeParenteses/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static final int testCases = 8;
	private static NelsonOracle oracle;
	private static int now = 0;
	private static final long seed = 343;

	@BeforeAll
	static void randomSetup() throws IOException, InterruptedException {
		oracle = new NelsonOracle(basePath + "_1125_ParesDeParentesesOracle.c");
	}

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	private void generateInput(int n) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/main/resources/in"));
		bufferedWriter.write(Integer.toString(n) + System.lineSeparator());
		bufferedWriter.close();
	}

	@RepeatedTest(testCases)
	void allTests() throws IOException, InterruptedException {

		generateInput(now ++);
		final String oracleAnswer = oracle.getAnswer();

		System.setIn(new FileInputStream("./src/main/resources/in"));
		outputStream.reset();
		_1125_ParesDeParenteses.HuxleyCode.main(null);
		final String myAnswer = outputStream.toString();

		String input = InOutReader.getStringFromFile("./src/main/resources/in");

		assertEquals(oracleAnswer, myAnswer, "Failed test case of input: <" + input + ">");
	}
}
