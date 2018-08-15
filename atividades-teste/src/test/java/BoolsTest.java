import br.ufal.ic.atividades.teste.BooleanArrayAsList;
import br.ufal.ic.atividades.teste.Bools;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoolsTest {

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
    Bools bools = new Bools();

    @Test
    void compare() {
        assertAll("compare method is failing",
                () -> assertTrue(bools.compare(true, false) > 0, "true should be > false"),
                () -> assertEquals(0, bools.compare(true, true), "true should be == true"),
                () -> assertEquals(0, bools.compare(false, false), "false shoudl be == false"),
                () -> assertTrue(bools.compare(false, true) < 0, "false should be < true")
        );
    }

    @Test
    void contains() {
        assertAll("contains method is failing",
                () -> assertFalse(bools.contains(ttArray, false), "ttArray shouldn't contain false"),
                () -> assertTrue(bools.contains(ttArray, true), "ttArray should contain true"),
                () -> assertFalse(bools.contains(ffArray, true), "ffArray shouldn't contain true"),
                () -> assertTrue(bools.contains(ffArray, false), "ffArray should contain false"),
                () -> assertTrue(bools.contains(tftArray, true), "tftArray should contain true"),
                () -> assertTrue(bools.contains(tftArray, false), "tftArray should contain false")
        );
    }

    @Test
    void indexOfSingle() {
        assertAll("indexOfSingle method is failing",
                () -> assertEquals(0, bools.indexOf(ttArray, true), "Position of true on ttArray should be 0"),
                () -> assertEquals(-10, bools.indexOf(ttArray, false), "false shouldn't be on ttArray"),
                () -> assertEquals(0, bools.indexOf(ffArray, false), "Position of false on ffArray should be 0"),
                () -> assertEquals(-10, bools.indexOf(ffArray, true), "true shouldn't be on ffArray"),
                () -> assertEquals(0, bools.indexOf(tftArray, true), "Position of true on tftArray should be 0"),
                () -> assertEquals(1, bools.indexOf(tftArray, false), "Position of false on tftArray should be 1")
        );
    }

    @Test
    void indexOfArray() {
        assertAll("indexOfArray method is failing",
                () -> assertEquals(0, bools.indexOf(ttfftftArray, ttArray), "Position of ttArray on ttfftftArray should be 0"),
                () -> assertEquals(2, bools.indexOf(ttfftftArray, ffArray), "Position of ffArray on ttfftftArray should be 2"),
                () -> assertEquals(4, bools.indexOf(ttfftftArray, tftArray), "Position of tftArray on ttfftftArray should be 4"),
                () -> assertEquals(-10, bools.indexOf(ttfftftArray, ffffArray), "ffffArray shouldn't be on ttfftftArray"),
                () -> assertEquals(-10, bools.indexOf(tffArray, fffArray), "fffArray shouldn't be on tffArray"),
                () -> assertEquals(-1, bools.indexOf(ttfftftArray, emptyArray), "emptyArray shouldn't be searched"),
                () -> assertEquals(-10, bools.indexOf(emptyArray, tArray), "true shouldn't be on emptyArray"),
                () -> assertEquals(-10, bools.indexOf(emptyArray, fArray), "false shouldn't be on emptyArray"),
                () -> assertThrows(NullPointerException.class, () -> bools.indexOf(null, emptyArray), "should throw NullPointerException"),
                () -> assertThrows(NullPointerException.class, () -> bools.indexOf(emptyArray, null), "should throw NullPointerException")
        );
    }

    @Test
    void lastIndexOf() {
        assertAll("lastIndexOf method is failing",
                () -> assertEquals(1, bools.lastIndexOf(ttArray, true), "Position of true on ttArray should be 1"),
                () -> assertEquals(-10, bools.lastIndexOf(ttArray, false), "false shouldn't be on ttArray"),
                () -> assertEquals(1, bools.lastIndexOf(ffArray, false), "Position of false on ffArray should be 1"),
                () -> assertEquals(-10, bools.lastIndexOf(ffArray, true), "true shouldn't be on ffArray"),
                () -> assertEquals(2, bools.lastIndexOf(tftArray, true), "Position of true on tftArray should be 2"),
                () -> assertEquals(1, bools.lastIndexOf(tftArray, false), "Position of false on tftArray should be 1"),
                () -> assertEquals(0, bools.lastIndexOf(tffArray, true), "Position of true on tffArray should be 0")
        );
    }

    @Test
    void concat() {
        assertAll("concat method is failing",
                () -> assertArrayEquals(fffArray, bools.concat(fArray, ffArray), "{false}+{false, false} should be {false, false, false}"),
                () -> assertArrayEquals(tffArray, bools.concat(tArray, ffArray), "{true}+{false, false} should be {true, false, false}"),
                () -> assertArrayEquals(fffArray, bools.concat(fArray, fArray, fArray), "{false}+{false}+{false} should be {false, false, false}"),
                () -> assertArrayEquals(emptyArray, bools.concat(emptyArray, emptyArray), "emptyArray + emptyArray should remain emptyArray"),
                () -> assertArrayEquals(tArray, bools.concat(emptyArray, tArray), "emptyArray + {true} should remain {true}"),
                () -> assertArrayEquals(fArray, bools.concat(emptyArray, fArray), "emptyArray + {false} should remain {false}")
        );
    }

    @Test
    void toArray() {
        ArrayList<Boolean> fffArrayCollection = new ArrayList<>(); for (int i = 0; i < fffArray.length; i ++) fffArrayCollection.add(fffArray[i]);
        ArrayList<Boolean> ttArrayCollection = new ArrayList<>(); for (int i = 0; i < ttArray.length; i ++) ttArrayCollection.add(ttArray[i]);
        BooleanArrayAsList fffArrayAsBoolean = new BooleanArrayAsList(fffArray, bools);
        BooleanArrayAsList ttArrayAsBoolean = new BooleanArrayAsList(ttArray, bools);
        ArrayList<Boolean> nullArrayCollection = new ArrayList<>(); nullArrayCollection.add(null);
        assertAll("toArray method is failing",
                () -> assertArrayEquals(fffArray, bools.toArray(fffArrayCollection), "fffArrayList to Array made a different array"),
                () -> assertArrayEquals(fffArray, bools.toArray(fffArrayAsBoolean), "fffBooleanArray to Array made a different array"),
                () -> assertArrayEquals(ttArray, bools.toArray(ttArrayCollection), "ttArrayList to Array made a different array"),
                () -> assertArrayEquals(ttArray, bools.toArray(ttArrayAsBoolean), "ttBooleanArray to Array made a different array"),
                () -> assertThrows(NullPointerException.class, () -> bools.toArray(nullArrayCollection), "Should have thrown NullPointerException")
        );
    }

    @Test
    void asList() {
        List<Boolean> ttArrayAsList = new ArrayList<>(); for (int i = 0; i < ttArray.length; i ++) ttArrayAsList.add(ttArray[i]);
        List<Boolean> fffArrayAsList = new ArrayList<>(); for (int i = 0; i < fffArray.length; i ++) fffArrayAsList.add(fffArray[i]);
        List<Boolean> tffArrayAsList = new ArrayList<>(); for (int i = 0; i < tffArray.length; i ++) tffArrayAsList.add(tffArray[i]);
        List<Boolean> emptyArrayAsList = new ArrayList<>();
        List<Boolean> tArrayAsList = new ArrayList<>(); for (int i = 0; i < tArray.length; i ++) tArrayAsList.add(tArray[i]);
        assertAll("asList method is failing",
                () -> assertIterableEquals(ttArrayAsList, bools.asList(ttArray), "ttArray asList made a different list"),
                () -> assertIterableEquals(fffArrayAsList, bools.asList(fffArray), "fffArray asList made a different list"),
                () -> assertIterableEquals(tffArrayAsList, bools.asList(true, false, false), "tffArray asList made a different list"),
                () -> assertIterableEquals(emptyArrayAsList, bools.asList(emptyArray), "emptyArray asList made a non empty list"),
                () -> assertIterableEquals(emptyArrayAsList, bools.asList(), "emptyArray asList made a non empty list"),
                () -> assertIterableEquals(tArrayAsList, bools.asList(tArray), "tArray asList made a different list")
        );
    }

    @Test
    void countTrue() {
        boolean[] true101Array = new boolean[101]; for (int i = 0; i < true101Array.length; i ++) true101Array[i] = true;
        assertAll("countTrue method is failing",
                () -> assertEquals(1, bools.countTrue(tArray), "tArray should have 1 true"),
                () -> assertEquals(0, bools.countTrue(fArray), "fArray should have 0 true"),
                () -> assertEquals(0, bools.countTrue(emptyArray), "emptyArray should have 0 true"),
                () -> assertEquals(1, bools.countTrue(tffArray), "tffArray should have 1 true"),
                () -> assertEquals(4, bools.countTrue(ttfftftArray), "ttfftftArray should have 4 trues"),
                () -> assertEquals(101, bools.countTrue(true101Array), "true101Array should have 101 trues")
        );
    }

    @Test
    void checkNotNull() {
        assertAll("checkNotNull method is failing",
                () -> assertThrows(NullPointerException.class, () -> bools.checkNotNull(null), "Should have thrown NullPointerException"),
                () -> assertEquals(true, bools.checkNotNull(true), "checkNotNull(true) should have returned true"),
                () -> assertEquals(false, bools.checkNotNull(false), "checkNotNull(false) should have returned false")
        );
    }

    @Test
    void checkElementIndex() {
        ArrayList<Executable> executables = new ArrayList<>();
        for (int j = 0; j < 5; j ++) {
            final int i = j;
            executables.add(() -> assertEquals(i, bools.checkElementIndex(i, 5, ""), "checkElement("+i+") should have returned " + i));
        }
        assertAll("checkElementIndex method is failing",
	        () -> assertAll("checkElementIndex method is failing on exceptions",
		        () -> assertThrows(IndexOutOfBoundsException.class, () -> bools.checkElementIndex(10, 10, ""), "Should have thrown IndexOutOfBoundsException because index >= size"),
		        () -> assertThrows(IndexOutOfBoundsException.class, () -> bools.checkElementIndex(-1, 10, ""), "Should have thrown IndexOutOfBoundsException because index < 0"),
		        () -> assertThrows(IllegalArgumentException.class, () -> bools.checkElementIndex(0, -1, ""), "Should have thrown IllegalArgumentException")
	        ),
	        () -> assertAll("checkElementIndex method is failing on regular indexes",
		        executables
	        )
        );
    }
}
