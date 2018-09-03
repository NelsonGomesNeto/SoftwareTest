import _186_Fusao.HuxleyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class _186_FusaoTest {

	private String basePath = "./src/test/resources/_186_Fusao/";
	private String ls = System.lineSeparator();
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void example() throws FileNotFoundException {
		String expected = "N" + ls + "S" + ls + "S" + ls;
		System.setIn(new FileInputStream(basePath + "example.in"));
		_186_Fusao.HuxleyCode.main(null);
		assertEquals(expected, outputStream.toString(), "Failing on example test case");
	}

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
			() -> assertAll("Merging is faling",
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
}
