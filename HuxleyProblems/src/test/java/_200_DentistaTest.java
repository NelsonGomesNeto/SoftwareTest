import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.Random;

public class _200_DentistaTest {

	private static final String basePath = "./src/test/resources/_200_Dentista/";
	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static String[] in, out;
	private static NelsonOracle oracle;
	private static final int seed = 343;
	private static Random random = new Random();

	@BeforeAll
	static void setupAll() throws IOException, InterruptedException {
		oracle = new NelsonOracle(basePath + "_200_Dentista.cpp");
		random.setSeed(seed);
		in = new File(basePath + "in/").list();
		out = new File(basePath + "out/").list();
		System.setOut(new PrintStream(outputStream));
	}

	@BeforeEach
	void resetOutput() { outputStream.reset(); }

	@RepeatedTest(3)
	void repeatedTest(RepetitionInfo repetitionInfo) throws IOException {
		int i = repetitionInfo.getCurrentRepetition() - 1;
		String expected = InOutReader.getStringFromFile(basePath + "out/" + out[i]);
		System.setIn(new FileInputStream(basePath + "in/" + in[i]));
		_200_Dentista.HuxleyCode.main(null);
		assertEquals(expected, InOutReader.uniformString(outputStream.toString()), "Failing " + in[i] + " test case");
	}

	private void generateInput() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NelsonOracle.in));
		Integer n = random.nextInt(9999) + 1;
		bufferedWriter.write(n.toString() + "\n");
		for (int i = 0; i < n; i ++) {
			Integer start = random.nextInt(1000000), end = random.nextInt(1000000);
			bufferedWriter.write(start.toString() + " " + end.toString() + "\n");
		}
		bufferedWriter.close();
	}

	@RepeatedTest(10)
	void randomTest() throws IOException, InterruptedException {

		generateInput();
		final String oracleAnswer = oracle.getAnswer();

		System.setIn(new FileInputStream(NelsonOracle.in));
		_200_Dentista.HuxleyCode.main(null);
		final String myAnswer = InOutReader.uniformString(outputStream.toString());

		String input = InOutReader.getStringFromFile(NelsonOracle.in);

		assertEquals(oracleAnswer, myAnswer, "Failed test case of input: <" + input + ">");
	}
}
