import _160_Gincana.HuxleyCode;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

public class _160_GincanaTest {

	private static final String basePath = "./src/test/resources/_160_Gincana/";
	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static String[] in, out;
	private static NelsonOracle oracle;
	private static final int seed = 343;
	private static Random random = new Random();

	@BeforeAll
	static void setupAll() throws IOException, InterruptedException {
		oracle = new NelsonOracle(basePath + "_160_Gincana.cpp");
		random.setSeed(seed);
		in = new File(basePath + "in/").list();
		out = new File(basePath + "out/").list();
		Arrays.sort(in); Arrays.sort(out);
		System.setOut(new PrintStream(outputStream));
	}

	@BeforeEach
	void resetOutput() { outputStream.reset(); }

	@Test
	void disjointSet() {
		HuxleyCode.DisjointSet disjointSet = new HuxleyCode.DisjointSet(3);

		assertAll("DisjointSet is failing",
			() -> assertAll("Disjoint Set creation has bugs",
				() -> assertFalse(disjointSet.isSameSet(0, 1), "0 shouldn't be connected to 1"),
				() -> assertFalse(disjointSet.isSameSet(0, 2), "0 shouldn't be connected to 2"),
				() -> assertFalse(disjointSet.isSameSet(1, 2), "1 shouldn't be connected to 2"),
				() -> assertEquals(1, disjointSet.setSize(0), "0 should have size 1"),
				() -> assertEquals(1, disjointSet.setSize(1), "1 should have size 1"),
				() -> assertEquals(1, disjointSet.setSize(2), "2 should have size 1"),
				() -> assertEquals(0, disjointSet.root(0), "0 root should be 0")
			),
			() -> assertAll("Equal elements aren't in the same set",
				() -> assertTrue(disjointSet.isSameSet(0, 0), "Element 0 isn't in it's own set"),
				() -> assertTrue(disjointSet.isSameSet(1, 1), "Element 1 isn't in it's own set"),
				() -> assertTrue(disjointSet.isSameSet(2, 2), "Element 2 isn't in it's own set")
			),
			() -> assertAll("Merging is failing",
				() -> disjointSet.merge(0, 1),
				() -> assertEquals(2, disjointSet.setSize(0), "0 should have size 2"),
				() -> assertEquals(2, disjointSet.setSize(1), "1 should have size 2"),
				() -> assertTrue(disjointSet.isSameSet(0, 1), "0 and 1 should be in the same set"),
				() -> disjointSet.merge(2, 0),
				() -> assertEquals(3, disjointSet.setSize(0), "0 should have size 3"),
				() -> assertEquals(3, disjointSet.setSize(1), "1 should have size 3"),
				() -> assertEquals(3, disjointSet.setSize(2), "2 should have size 3"),
				() -> assertTrue(disjointSet.isSameSet(0, 1), "0 and 1 should be in the same set"),
				() -> assertTrue(disjointSet.isSameSet(0, 2), "0 and 2 should be in the same set"),
				() -> assertTrue(disjointSet.isSameSet(1, 2), "1 and 2 should be in the same set"),
				() -> disjointSet.merge(2, 2),
				() -> assertEquals(3, disjointSet.setSize(2), "2 should maintain size 3")
			)
		);
	}

	@RepeatedTest(3)
	void repeatedTest(RepetitionInfo repetitionInfo) throws IOException {
		int i = repetitionInfo.getCurrentRepetition() - 1;
		String expected = InOutReader.getStringFromFile(basePath + "out/" + out[i]);
		System.setIn(new FileInputStream(basePath + "in/" + in[i]));
		final String myAnswer = assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			_160_Gincana.HuxleyCode.main(null);
			return(InOutReader.uniformString(outputStream.toString()));
		});
		assertEquals(expected, myAnswer, "Failing " + in[i] + " test case");
	}

	private void generateInput() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NelsonOracle.in));
		Integer n = random.nextInt(999) + 1, m = random.nextInt(5000);
		bufferedWriter.write(n.toString() +  " " + m.toString() + "\n");
		for (int i = 0; i < m; i ++) {
			Integer u = random.nextInt(n - 1) + 1, v = random.nextInt(n - 1) + 1;
			bufferedWriter.write(u.toString() + " " + v.toString() + "\n");
		}
		bufferedWriter.close();
	}

	@RepeatedTest(10)
	void randomTest() throws IOException, InterruptedException {

		generateInput();
		final String oracleAnswer = oracle.getAnswer();

		System.setIn(new FileInputStream(NelsonOracle.in));
		final String myAnswer = assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			_160_Gincana.HuxleyCode.main(null);
			return(InOutReader.uniformString(outputStream.toString()));
		});

		String input = InOutReader.getStringFromFile(NelsonOracle.in);

		assertEquals(oracleAnswer, myAnswer, "Failed test case of input: <" + input + ">");
	}
}
