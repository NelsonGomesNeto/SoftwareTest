import br.ufal.ic.atividades.teste.BooleanArrayAsList;
import br.ufal.ic.atividades.teste.Bools;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoolsTest {

    boolean[] singleTrueArray = {true};
    boolean[] singleFalseArray = {false};
    boolean[] trueArray = {true, true};
    boolean[] falseArray = {false, false};
    boolean[] bothArray = {true, false, true};
    boolean[] fffArray = {false, false, false};
    boolean[] tffArray = {true, false, false};
    boolean[] bigFalseArray = {false, false, false, false};
    boolean[] bigArray = {true, true, false, false, true, false, true};
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
                () -> assertFalse(bools.contains(trueArray, false), "trueArray shouldn't contain false"),
                () -> assertTrue(bools.contains(trueArray, true), "trueArray should contain true"),
                () -> assertFalse(bools.contains(falseArray, true), "falseArray shouldn't contain true"),
                () -> assertTrue(bools.contains(falseArray, false), "falseArray should contain false"),
                () -> assertTrue(bools.contains(bothArray, true), "bothArray should contain true"),
                () -> assertTrue(bools.contains(bothArray, false), "bothArray should contain false")
        );
    }

    @Test
    void indexOfSingle() {
        assertAll("indexOfSingle method is failing",
                () -> assertEquals(0, bools.indexOf(trueArray, true), "Position of true on trueArray should be 0"),
                () -> assertEquals(-10, bools.indexOf(trueArray, false), "false shouldn't be on trueArray"),
                () -> assertEquals(0, bools.indexOf(falseArray, false), "Position of false on falseArray should be 0"),
                () -> assertEquals(-10, bools.indexOf(falseArray, true), "true shouldn't be on falseArray"),
                () -> assertEquals(0, bools.indexOf(bothArray, true), "Position of true on bothArray should be 0"),
                () -> assertEquals(1, bools.indexOf(bothArray, false), "Position of false on bothArray should be 1")
        );
    }

    @Test
    void indexOfArray() {
        assertAll("indexOfArray method is failing",
                () -> assertEquals(0, bools.indexOf(bigArray, trueArray), "Position of trueArray on bigArray should be 0"),
                () -> assertEquals(2, bools.indexOf(bigArray, falseArray), "Position of falseArray on bigArray should be 2"),
                () -> assertEquals(4, bools.indexOf(bigArray, bothArray), "Position of bothArray on bigArray should be 4"),
                () -> assertEquals(-10, bools.indexOf(bigArray, bigFalseArray), "bigFalseArray shouldn't be on bigArray"),
                () -> assertEquals(-10, bools.indexOf(tffArray, fffArray), "fffArray shouldn't be on tffArray"),
                () -> assertEquals(-1, bools.indexOf(bigArray, emptyArray), "emptyArray shouldn't be searched"),
                () -> assertEquals(-10, bools.indexOf(emptyArray, singleTrueArray), "true shouldn't be on emptyArray"),
                () -> assertEquals(-10, bools.indexOf(emptyArray, singleFalseArray), "false shouldn't be on emptyArray"),
                () -> assertThrows(NullPointerException.class, () -> bools.indexOf(null, emptyArray), "should throw NullPointerException"),
                () -> assertThrows(NullPointerException.class, () -> bools.indexOf(emptyArray, null), "should throw NullPointerException")
        );
    }

    @Test
    void lastIndexOf() {
        assertAll("lastIndexOf method is failing",
                () -> assertEquals(1, bools.lastIndexOf(trueArray, true), "Position of true on trueArray should be 1"),
                () -> assertEquals(-10, bools.lastIndexOf(trueArray, false), "false shouldn't be on trueArray"),
                () -> assertEquals(1, bools.lastIndexOf(falseArray, false), "Position of false on falseArray should be 1"),
                () -> assertEquals(-10, bools.lastIndexOf(falseArray, true), "true shouldn't be on falseArray"),
                () -> assertEquals(2, bools.lastIndexOf(bothArray, true), "Position of true on bothArray should be 2"),
                () -> assertEquals(1, bools.lastIndexOf(bothArray, false), "Position of false on bothArray should be 1"),
                () -> assertEquals(0, bools.lastIndexOf(tffArray, true), "Position of true on tffArray should be 0")
        );
    }

    @Test
    void concat() {
        assertAll("concat method is failing",
                () -> assertArrayEquals(fffArray, bools.concat(singleFalseArray, falseArray), "{false}+{false, false} should be {false, false, false}"),
                () -> assertArrayEquals(tffArray, bools.concat(singleTrueArray, falseArray), "{true}+{false, false} should be {true, false, false}"),
                () -> assertArrayEquals(fffArray, bools.concat(singleFalseArray, singleFalseArray, singleFalseArray), "{false}+{false}+{false} should be {false, false, false}"),
                () -> assertArrayEquals(emptyArray, bools.concat(emptyArray, emptyArray), "emptyArray + emptyArray should remain emptyArray"),
                () -> assertArrayEquals(singleTrueArray, bools.concat(emptyArray, singleTrueArray), "emptyArray + {true} should remain {true}"),
                () -> assertArrayEquals(singleFalseArray, bools.concat(emptyArray, singleFalseArray), "emptyArray + {false} should remain {false}")
        );
    }

    @Test
    void toArray() {
        ArrayList<Boolean> fffArrayCollection = new ArrayList<>(); for (int i = 0; i < fffArray.length; i ++) fffArrayCollection.add(fffArray[i]);
        ArrayList<Boolean> trueArrayCollection = new ArrayList<>(); for (int i = 0; i < trueArray.length; i ++) trueArrayCollection.add(trueArray[i]);
        BooleanArrayAsList fffArrayAsBoolean = new BooleanArrayAsList(fffArray, bools);
        BooleanArrayAsList trueArrayAsBoolean = new BooleanArrayAsList(trueArray, bools);
        ArrayList<Boolean> nullArrayCollection = new ArrayList<>(); nullArrayCollection.add(null);
        assertAll("toArray method is failing",
                () -> assertArrayEquals(fffArray, bools.toArray(fffArrayCollection), "fffArrayList to Array made a different array"),
                () -> assertArrayEquals(fffArray, bools.toArray(fffArrayAsBoolean), "fffBooleanArray to Array made a different array"),
                () -> assertArrayEquals(trueArray, bools.toArray(trueArrayCollection), "trueArrayList to Array made a different array"),
                () -> assertArrayEquals(trueArray, bools.toArray(trueArrayAsBoolean), "trueBooleanArray to Array made a different array"),
                () -> assertThrows(NullPointerException.class, () -> bools.toArray(nullArrayCollection), "Should have thrown NullPointerException")
        );
    }

    @Test
    void asList() {
        List<Boolean> trueArrayAsList = new ArrayList<>(); for (int i = 0; i < trueArray.length; i ++) trueArrayAsList.add(trueArray[i]);
        List<Boolean> fffArrayAsList = new ArrayList<>(); for (int i = 0; i < fffArray.length; i ++) fffArrayAsList.add(fffArray[i]);
        List<Boolean> tffArrayAsList = new ArrayList<>(); for (int i = 0; i < tffArray.length; i ++) tffArrayAsList.add(tffArray[i]);
        List<Boolean> emptyArrayAsList = new ArrayList<>();
        List<Boolean> singleTrueArrayAsList = new ArrayList<>(); for (int i = 0; i < singleTrueArray.length; i ++) singleTrueArrayAsList.add(singleTrueArray[i]);
        assertAll("asList method is failing",
                () -> assertIterableEquals(trueArrayAsList, bools.asList(trueArray), "trueArray asList made a different list"),
                () -> assertIterableEquals(fffArrayAsList, bools.asList(fffArray), "fffArray asList made a different list"),
                () -> assertIterableEquals(tffArrayAsList, bools.asList(true, false, false), "tffArray asList made a different list"),
                () -> assertIterableEquals(emptyArrayAsList, bools.asList(emptyArray), "emptyArray asList made a non empty list"),
                () -> assertIterableEquals(emptyArrayAsList, bools.asList(), "emptyArray asList made a non empty list"),
                () -> assertIterableEquals(singleTrueArrayAsList, bools.asList(singleTrueArray), "singleTrueArray asList made a different list")
        );
    }

    @Test
    void countTrue() {
        boolean[] true101Array = new boolean[101]; for (int i = 0; i < true101Array.length; i ++) true101Array[i] = true;
        assertAll("countTrue method is failing",
                () -> assertEquals(1, bools.countTrue(singleTrueArray), "singleTrueArray should have 1 true"),
                () -> assertEquals(0, bools.countTrue(singleFalseArray), "singleFalseArray should have 0 true"),
                () -> assertEquals(0, bools.countTrue(emptyArray), "emptyArray should have 0 true"),
                () -> assertEquals(1, bools.countTrue(tffArray), "tffArray should have 1 true"),
                () -> assertEquals(4, bools.countTrue(bigArray), "bigArray should have 4 trues"),
                () -> assertEquals(101, bools.countTrue(true101Array), "true101Array should have 101 trues")
        );
    }

    @Test
    void checkNotNull() {
        Boolean t = true, f = false;
        assertAll("checkNotNull method is failing",
                () -> assertThrows(NullPointerException.class, () -> bools.checkNotNull(null), "Should have thrown NullPointerException"),
                () -> assertEquals(t, bools.checkNotNull(t), "checkNotNull(true) should have returned true"),
                () -> assertEquals(f, bools.checkNotNull(f), "checkNotNull(false) should have returned false")
        );
    }

    @Test
    void checkElementIndex() {
        ArrayList<Executable> executables = new ArrayList<>();
        for (int j = 0; j < 5; j ++) {
            final int i = j;
            executables.add(() -> assertEquals(i, bools.checkElementIndex(i, 5, ""), "checkElement("+i+") should have returned " + i));
        }
        assertAll("checkElementIndex method is failing on exceptions",
                () -> assertThrows(IndexOutOfBoundsException.class, () -> bools.checkElementIndex(10, 10, ""), "Should have thrown IndexOutOfBoundsException because index >= size"),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> bools.checkElementIndex(-1, 10, ""), "Should have thrown IndexOutOfBoundsException because index < 0"),
                () -> assertThrows(IllegalArgumentException.class, () -> bools.checkElementIndex(0, -1, ""), "Should have thrown IllegalArgumentException")
        );
        assertAll("checkElementIndex method is failing on regular indexes",
                executables
        );
    }
}
