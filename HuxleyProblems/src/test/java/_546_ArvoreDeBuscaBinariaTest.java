import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class _546_ArvoreDeBuscaBinariaTest {

	private static final String ls = System.lineSeparator();
	private static final String basePath = "./src/test/resources/_546_ArvoreDeBuscaBinaria/";
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void treeReading() throws IOException {
		String expected = "(5(4(11(7()())(2()()))())(8(13()())(4()(1()()))))";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(basePath + "example.in"));
		String treeString = bufferedReader.readLine();
		_546_ArvoreDeBuscaBinaria.HuxleyCode.Tree tree = _546_ArvoreDeBuscaBinaria.HuxleyCode.Tree.buildTree(treeString);
		_546_ArvoreDeBuscaBinaria.HuxleyCode.Tree.printTree(tree, 0);
		assertEquals(expected, outputStream.toString(), "Tree wasn't read properly");
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "FALSO" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_546_ArvoreDeBuscaBinaria.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing example test case");
	}
}