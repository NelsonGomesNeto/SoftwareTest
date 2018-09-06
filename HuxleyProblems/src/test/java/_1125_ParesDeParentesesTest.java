import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.io.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class _1125_ParesDeParentesesTest {

	private static final String basePath = "./src/test/resources/_1125_ParesDeParenteses/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static final int testCases = 8;
	private static int now = 0;
	private static final long seed = 343;

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	private void generateInput(int n) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/main/resources/in"));
		bufferedWriter.write(Integer.toString(n));
		bufferedWriter.close();
	}

	@RepeatedTest(testCases)
	void allTests() throws IOException, InterruptedException {
		NelsonOracle oracle = new NelsonOracle("./src/main/resources/_1125_ParesDeParentesesOracle.c");

		generateInput(now ++);
		final String oracleAnswer = oracle.getAnswer();

		System.setIn(new FileInputStream("./src/main/resources/in"));
		outputStream.reset();
		_1125_ParesDeParenteses.HuxleyCode.main(null);
		final String myAnswer = outputStream.toString();

		String input = oracle.getStringFromFile("./src/main/resources/in");

		assertEquals(oracleAnswer, myAnswer, "Failed test case of input: <" + input + ">");
	}
}
