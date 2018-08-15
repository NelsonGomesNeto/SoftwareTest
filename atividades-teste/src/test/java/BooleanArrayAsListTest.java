import br.ufal.ic.atividades.teste.BooleanArrayAsList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanArrayAsListTest {

	boolean[] tArray = {true};
	boolean[] fArray = {false};
	boolean[] ttArray = {true, true};
	boolean[] ffArray = {false, false};
	boolean[] tftArray = {true, false, true};
	boolean[] fffArray = {false, false, false};
	boolean[] tffArray = {true, false, false};
	boolean[] ffffArray = {false, false, false, false};
	boolean[] ttfftftArray = {true, true, false, false, true, false, true};
	boolean[] emptyArray = {};
	MockBools mockBools = new MockBools();
	BooleanArrayAsList tBooleanArray = new BooleanArrayAsList(tArray, mockBools);
	BooleanArrayAsList fBooleanArray = new BooleanArrayAsList(fArray, mockBools);
	BooleanArrayAsList ttfftftBooleanArray = new BooleanArrayAsList(ttfftftArray, mockBools);
	BooleanArrayAsList emptyBooleanArray = new BooleanArrayAsList(emptyArray, mockBools);

	@Test
	void size() {
		assertAll("size method is failing",
			() -> assertEquals(1, tBooleanArray.size()),
			() -> assertEquals(1, fBooleanArray.size()),
			() -> assertEquals(7, ttfftftBooleanArray.size()),
			() -> assertEquals(0, emptyBooleanArray.size())
		);
	}

	@Test
	void isEmpty() {
		assertAll("isEmpty method is failing",
			() -> assertFalse(tBooleanArray.isEmpty()),
			() -> assertFalse(fBooleanArray.isEmpty()),
			() -> assertFalse(ttfftftBooleanArray.isEmpty()),
			() -> assertTrue(emptyBooleanArray.isEmpty())
		);
	}
	
	@Test
	void get() {
		ArrayList<Executable> executables = new ArrayList<>();
		for (int j = 0; j < ttfftftArray.length; j ++) {
			final int i = j;
			executables.add(() -> assertEquals(ttfftftArray[i], ttfftftBooleanArray.get(i), "Position "+i+" should have "+ttfftftArray[i]+" but is "+!ttfftftArray[i]));
		}
		assertAll("get method is failing",
			() -> assertAll("get method is failing on exceptions",
				() -> assertThrows(IndexOutOfBoundsException.class, () -> ttfftftBooleanArray.get(-1)),
				() -> assertThrows(IndexOutOfBoundsException.class, () -> ttfftftBooleanArray.get(ttfftftArray.length))
			),
			() -> assertAll("get method is failing on regular indexes",
				executables
			)
		);
	}

	@Test
	void contains() {
		assertAll("contains method is failing",
			() -> assertTrue(tBooleanArray.contains(true)),
			() -> assertFalse(tBooleanArray.contains(false)),
			() -> assertTrue(ttfftftBooleanArray.contains(true)),
			() -> assertTrue(ttfftftBooleanArray.contains(false)),
			() -> assertFalse(tBooleanArray.contains(null))
		);
	}

	@Test
	void indexOf() {
		assertAll("indexOf method is failing",
			() -> assertEquals(-1, emptyBooleanArray.indexOf(true)),
			() -> assertEquals(0, tBooleanArray.indexOf(true)),
			() -> assertEquals(-1, tBooleanArray.indexOf(false)),
			() -> assertEquals(0, ttfftftBooleanArray.indexOf(true)),
			() -> assertEquals(2, ttfftftBooleanArray.indexOf(false)),
			() -> assertEquals(-1, ttfftftBooleanArray.indexOf(null))
		);
	}

	@Test
	void lastIndexOf() {
		assertAll("lastIndexOf method is failing",
			() -> assertEquals(-1, emptyBooleanArray.lastIndexOf(true)),
			() -> assertEquals(0, tBooleanArray.lastIndexOf(true)),
			() -> assertEquals(-1, tBooleanArray.lastIndexOf(false)),
			() -> assertEquals(6, ttfftftBooleanArray.lastIndexOf(true)),
			() -> assertEquals(5, ttfftftBooleanArray.lastIndexOf(false)),
			() -> assertEquals(-1, ttfftftBooleanArray.lastIndexOf(null))
		);
	}

	@Test
	void set() {
		ArrayList<Executable> executables = new ArrayList<>();
		ArrayList<Executable> executablesAfterSet = new ArrayList<>();
		for (int j = 0; j < ttfftftArray.length; j ++) {
			final int i = j;
			executables.add(() -> assertEquals(ttfftftArray[i], ttfftftBooleanArray.set(i, !ttfftftArray[i]), "Position "+i+" should have "+ttfftftArray[i]+" but is "+!ttfftftArray[i]));
			final boolean lol = !ttfftftArray[i];
			executablesAfterSet.add(() -> assertEquals(lol, ttfftftBooleanArray.set(i, ttfftftArray[i]), "Position "+i+" should have "+lol+" but is "+ttfftftArray[i]));
		}
		assertAll("set method is failing",
			() -> assertAll("set method is failing on returning value previous to set",
				executables
			),
			() -> assertAll("set method is failing on saving value after set",
				executablesAfterSet
			)
		);
	}

	@Test
	void equals() {
		BooleanArrayAsList ttfftftBooleanDuplicate = new BooleanArrayAsList(ttfftftArray, mockBools);
		assertAll("equals method is failing",
			() -> assertTrue(ttfftftBooleanArray.equals(ttfftftBooleanDuplicate)),
			() -> assertTrue(emptyBooleanArray.equals(emptyBooleanArray)),
			() -> assertFalse(ttfftftBooleanArray.equals(tBooleanArray)),
			() -> assertFalse(ttfftftBooleanArray.equals(fBooleanArray)),
			() -> assertFalse(tBooleanArray.equals(fBooleanArray)),
			() -> assertFalse(ttfftftBooleanArray.equals(emptyBooleanArray)),
			() -> assertFalse(ttfftftBooleanArray.equals(null))
		);
	}

	@Test
	void hashCodeTest() {
		assertAll("hashCode method is failing",
			() -> assertEquals(1, emptyBooleanArray.hashCode()),
			() -> assertEquals(1262, tBooleanArray.hashCode()),
			() -> assertEquals(1106397192, ttfftftBooleanArray.hashCode())
		);
	}

	@Test
	void toStringTest() {
		assertAll("toString method is failing",
			() -> assertEquals("[]", emptyBooleanArray.toString()),
			() -> assertEquals("[true]", tBooleanArray.toString()),
			() -> assertEquals("[false]", fBooleanArray.toString()),
			() -> assertEquals("[true, true, false, false, true, false, true]", ttfftftBooleanArray.toString())
		);
	}


}