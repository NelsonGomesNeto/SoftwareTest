import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.Random;

public class _1138_DivisoresTest {

	private static final String basePath = "./src/test/resources/_1138_Divisores/";
	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static String[] in, out;
	private static NelsonOracle oracle;
	private static final int seed = 343;
	private static Random random = new Random();

	@BeforeAll
	static void setupAll() throws IOException, InterruptedException {
		oracle = new NelsonOracle(basePath + "_1138_Divisores.cpp");
		random.setSeed(seed);
		in = new File(basePath + "in/").list();
		out = new File(basePath + "out/").list();
		System.setOut(new PrintStream(outputStream));
	}

	@BeforeEach
	void resetOutput() { outputStream.reset(); }

	@RepeatedTest(2)
	void repeatedTest(RepetitionInfo repetitionInfo) throws IOException {
		int i = repetitionInfo.getCurrentRepetition() - 1;
		String expected = InOutReader.getStringFromFile(basePath + "out/" + out[i]);
		System.setIn(new FileInputStream(basePath + "in/" + in[i]));
		_1138_Divisores.HuxleyCode.main(null);
		assertEquals(expected, InOutReader.uniformString(outputStream.toString()), "Failing " + in[i] + " test case");
	}

	private void generateInput() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NelsonOracle.in));
		Integer a = random.nextInt(1000000000 - 1) + 1, b = random.nextInt(1000000000 - 1) + 1, c = random.nextInt(1000000000 - 1) + 1, d = random.nextInt(1000000000 - 1) + 1;
		bufferedWriter.write(a.toString() + " " + b.toString() + " " + c.toString() + " " + d.toString() + "\n");
		bufferedWriter.close();
	}

	@RepeatedTest(25)
	void randomTest() throws IOException, InterruptedException {

		generateInput();
		final String oracleAnswer = oracle.getAnswer();

		System.setIn(new FileInputStream(NelsonOracle.in));
		_1138_Divisores.HuxleyCode.main(null);
		final String myAnswer = InOutReader.uniformString(outputStream.toString());

		String input = InOutReader.getStringFromFile(NelsonOracle.in);

		assertEquals(oracleAnswer, myAnswer, "Failed test case of input: <" + input + ">");
	}
}
