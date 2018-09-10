import _538_SomaMaxima.HuxleyCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class _538_SomaMaximaTest {

	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static String basePath = "./src/test/resources/_538_SomaMaxima/";
	private String path = basePath + "example.in";
	private int[][] mat;
	private int[][] accSum;
	private int size;
	private static NelsonOracle oracle;
	private static final int seed = 343;
	private static Random random = new Random();

	@BeforeAll
	static void setupAll() throws IOException, InterruptedException {
		random.setSeed(seed);
		System.setOut(new PrintStream(outputStream));
		oracle = new NelsonOracle(basePath + "_538_SomaMaxima.cpp");
	}

	@BeforeEach
	void setup() {
		outputStream.reset();
	}

	private void load() throws IOException {
		Scanner scanner = new Scanner(new FileInputStream(path));
		size = scanner.nextInt();
		mat = new int[size][size];
		for (int i = 0; i < size; i ++)
			for (int j = 0; j < size; j ++)
				mat[i][j] = scanner.nextInt();
		accSum = _538_SomaMaxima.HuxleyCode.buildAccumulatedSum(mat);
		System.setIn(new FileInputStream(path));
	}

	@Test
	void example() throws IOException {
		path = basePath + "example.in";
		load();
		String expected = bruteForce();
		_538_SomaMaxima.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}

	@Test
	void allNegatives() throws IOException {
		path = basePath + "allNegatives.in";
		load();
		String expected = bruteForce();
		_538_SomaMaxima.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing all negatives test case");
	}

	@Test
	void accumulatedSum() throws IOException {
		ArrayList<Executable> executables = new ArrayList<>();
		load();
		for (int ei = 0; ei < size; ei ++)
			for (int ej = 0; ej < size; ej ++) {
				int sum = 0;
				for (int i = 0; i <= ei; i ++)
					for (int j = 0; j <= ej; j ++)
						sum += mat[i][j];
				Integer finalSum = sum, finalEi = ei, finalEj = ej;
				executables.add(() -> assertEquals((int) finalSum, accSum[finalEi][finalEj], "Position: (" + finalEi.toString() + ", " + finalEj.toString() + ")"));
			}
		assertAll("Accumulated sum is failing", executables);
	}

	@Test
	void getSum() throws IOException {
		ArrayList<Executable> executables = new ArrayList<>();
		load();
		for (int li = 0; li < size; li ++)
			for (int lj = 0; lj < size; lj ++)
				for (int hi = li; hi < size; hi ++)
					for (int hj = lj; hj < size; hj ++) {
						int sum = 0;
						for (int i = li; i <= hi; i ++)
							for (int j = lj; j <= hj; j ++)
								sum += mat[i][j];
						final Integer finalSum = sum, gotSum = _538_SomaMaxima.HuxleyCode.getSum(accSum, li, lj, hi, hj);
						final Integer finalLi = li, finalLj = lj, finalHi = hi, finalHj = hj;
						executables.add(() -> assertEquals((int) finalSum, (int) gotSum, "Position: (" + finalLi.toString() + ", " + finalLj.toString() + ") - (" + finalHi.toString() + ", " + finalHj.toString() + ")"));
					}
		assertAll("Getting rectangle sum is failing", executables);
	}

	private int linearMaxSumBruteForce(int[] array) {
		int maxSum = array[0];
		for (int i = 0; i < size; i ++) {
			int sum = 0;
			for (int j = i; j < size; j ++) {
				sum += array[j];
				maxSum = Math.max(maxSum, sum);
			}
		}
		return(maxSum);
	}

	@Test
	void kadane() {
		ArrayList<Executable> executables = new ArrayList<>();
		for (int k = 0; k < size; k ++) {
			int[] array = mat[k];
			final Integer kadaneResult = HuxleyCode.kadane(array);
			final Integer expected = linearMaxSumBruteForce(array);
			executables.add(() -> assertEquals(expected, kadaneResult, "Wrong max sum on array: " + Arrays.toString(array)));
		}
		for (int j = 0; j < size; j ++) {
			int [] array = new int[size];
			for (int i = 0; i < size; i ++) array[i] = mat[i][j];
			final Integer kadaneResult = HuxleyCode.kadane(array);
			final Integer expected = linearMaxSumBruteForce(array);
			executables.add(() -> assertEquals(expected, kadaneResult, "Wrong max sum on array: " + Arrays.toString(array)));
		}
		assertAll("kadande is failing", executables);
	}

	private String bruteForce() {
		int maxSum = mat[0][0];
		for (int li = 0; li < size; li ++)
			for (int lj = 0; lj < size; lj ++)
				for (int hi = li; hi < size; hi ++)
					for (int hj = lj; hj < size; hj ++) {
						int sum = 0;
						for (int i = li; i <= hi; i++)
							for (int j = lj; j <= hj; j++)
								sum += mat[i][j];
						maxSum = Math.max(sum, maxSum);
					}
		return(Integer.toString(maxSum) + System.lineSeparator());
	}

	private void generateInput() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NelsonOracle.in));
		Integer n = random.nextInt(99) + 1;
		bufferedWriter.write(n.toString() + "\n");
		for (int i = 0; i < n; i ++)
			for (int j = 0; j < n; j ++)
				bufferedWriter.write(Integer.toString(127 - random.nextInt(254)) + (j <  n - 1 ? " " : "\n"));
		bufferedWriter.close();
	}

	@RepeatedTest(10)
	void randomTest() throws IOException, InterruptedException {

		generateInput();
		final String oracleAnswer = oracle.getAnswer();

		System.setIn(new FileInputStream(NelsonOracle.in));
		final String myAnswer = assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			_538_SomaMaxima.HuxleyCode.main(null);
			return(InOutReader.uniformString(outputStream.toString()));
		});

		String input = InOutReader.getStringFromFile(NelsonOracle.in);

		assertEquals(oracleAnswer, myAnswer, "Failed test case of input: <" + input + ">");
	}

}
