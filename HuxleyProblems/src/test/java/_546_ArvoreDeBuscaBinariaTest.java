import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class _546_ArvoreDeBuscaBinariaTest {

	private static final String basePath = "./src/test/resources/_546_ArvoreDeBuscaBinaria/";
	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static String[] in, out;

	@BeforeAll
	static void setupAll() throws IOException, InterruptedException {
		in = new File(basePath + "in/").list();
		out = new File(basePath + "out/").list();
		Arrays.sort(in); Arrays.sort(out);
		System.setOut(new PrintStream(outputStream));
	}

	@BeforeEach
	void resetOutput() { outputStream.reset(); }

	@RepeatedTest(5)
	void repeatedTest(RepetitionInfo repetitionInfo) throws IOException {
		int i = repetitionInfo.getCurrentRepetition() - 1;
		String expected = InOutReader.getStringFromFile(basePath + "out/" + out[i]);
		System.setIn(new FileInputStream(basePath + "in/" + in[i]));
		_546_ArvoreDeBuscaBinaria.HuxleyCode.main(null);
		assertEquals(expected, InOutReader.uniformString(outputStream.toString()), "Failing " + in[i] + " test case");
	}

	@Test
	void treeReading() throws IOException {
		String expected = "(5(4(11(7()())(2()()))())(8(13()())(4()(1()()))))";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(basePath + "in/example.in"));
		String treeString = bufferedReader.readLine();
		_546_ArvoreDeBuscaBinaria.HuxleyCode.Tree tree = _546_ArvoreDeBuscaBinaria.HuxleyCode.Tree.buildTree(treeString);
		_546_ArvoreDeBuscaBinaria.HuxleyCode.Tree.printTree(tree, 0);
		assertEquals(expected, outputStream.toString(), "Tree wasn't read properly");
	}
}