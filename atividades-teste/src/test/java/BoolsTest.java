import br.ufal.ic.atividades.teste.Bools;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

    @Test
    void compare() {
        assertAll("compare method is failing",
                () -> assertTrue(Bools.compare(true, false) > 0, "true should be > false"),
                () -> assertEquals(0, Bools.compare(true, true), "true should be == true"),
                () -> assertEquals(0, Bools.compare(false, false), "false shoudl be == false"),
                () -> assertTrue(Bools.compare(false, true) < 0, "false should be < true")
        );
    }

    @Test
    void contains() {
        assertAll("contains method is failing",
                () -> assertFalse(Bools.contains(trueArray, false), "trueArray shouldn't contain false"),
                () -> assertTrue(Bools.contains(trueArray, true), "trueArray should contain true"),
                () -> assertFalse(Bools.contains(falseArray, true), "falseArray shouldn't contain true"),
                () -> assertTrue(Bools.contains(falseArray, false), "falseArray should contain false"),
                () -> assertTrue(Bools.contains(bothArray, true), "bothArray should contain true"),
                () -> assertTrue(Bools.contains(bothArray, false), "bothArray should contain false")
        );
    }

    @Test
    void indexOfSingle() {
        assertAll("indexOfSingle method is failing",
                () -> assertEquals(0, Bools.indexOf(trueArray, true), "Position of true on trueArray should be 0"),
                () -> assertEquals(-10, Bools.indexOf(trueArray, false), "false shouldn't be on trueArray"),
                () -> assertEquals(0, Bools.indexOf(falseArray, false), "Position of false on falseArray should be 0"),
                () -> assertEquals(-10, Bools.indexOf(falseArray, true), "true shouldn't be on falseArray"),
                () -> assertEquals(0, Bools.indexOf(bothArray, true), "Position of true on bothArray should be 0"),
                () -> assertEquals(1, Bools.indexOf(bothArray, false), "Position of false on bothArray should be 1")
        );
    }

    @Test
    void indexOfArray() {
        assertAll("indexOfArray method is failing",
                () -> assertEquals(0, Bools.indexOf(bigArray, trueArray), "Position of trueArray on bigArray should be 0"),
                () -> assertEquals(2, Bools.indexOf(bigArray, falseArray), "Position of falseArray on bigArray should be 2"),
                () -> assertEquals(4, Bools.indexOf(bigArray, bothArray), "Position of bothArray on bigArray should be 4"),
                () -> assertEquals(-10, Bools.indexOf(bigArray, bigFalseArray), "bigFalseArray shouldn't be on bigArray"),
                () -> assertEquals(-10, Bools.indexOf(tffArray, fffArray), "fffArray shouldn't be on tffArray"),
                () -> assertEquals(-1, Bools.indexOf(bigArray, emptyArray), "emptyArray shouldn't be searched"),
                () -> assertEquals(-10, Bools.indexOf(emptyArray, singleTrueArray), "true shouldn't be on emptyArray"),
                () -> assertEquals(-10, Bools.indexOf(emptyArray, singleFalseArray), "false shouldn't be on emptyArray"),
                () -> assertThrows(AssertionError.class, () -> Bools.indexOf(null, emptyArray), "should throw AssertionError"),
                () -> assertThrows(AssertionError.class, () -> Bools.indexOf(emptyArray, null), "should throw AssertionError")
        );
    }

    @Test
    void lastIndexOf() {
        assertAll("lastIndexOf method is failing",
                () -> assertEquals(1, Bools.lastIndexOf(trueArray, true), "Position of true on trueArray should be 1"),
                () -> assertEquals(-10, Bools.lastIndexOf(trueArray, false), "false shouldn't be on trueArray"),
                () -> assertEquals(1, Bools.lastIndexOf(falseArray, false), "Position of false on falseArray should be 1"),
                () -> assertEquals(-10, Bools.lastIndexOf(falseArray, true), "true shouldn't be on falseArray"),
                () -> assertEquals(2, Bools.lastIndexOf(bothArray, true), "Position of true on bothArray should be 2"),
                () -> assertEquals(1, Bools.lastIndexOf(bothArray, false), "Position of false on bothArray should be 1"),
                () -> assertEquals(0, Bools.lastIndexOf(tffArray, true), "Position of true on tffArray should be 0")
        );
    }

    @Test
    void concat() {
        assertAll("concat method is failing",
                () -> assertArrayEquals(fffArray, Bools.concat(singleFalseArray, falseArray), "{false}+{false, false} should be {false, false, false}"),
                () -> assertArrayEquals(tffArray, Bools.concat(singleTrueArray, falseArray), "{true}+{false, false} should be {true, false, false}"),
                () -> assertArrayEquals(fffArray, Bools.concat(singleFalseArray, singleFalseArray, singleFalseArray), "{false}+{false}+{false} should be {false, false, false}"),
                () -> assertArrayEquals(emptyArray, Bools.concat(emptyArray, emptyArray), "emptyArray + emptyArray should remain emptyArray"),
                () -> assertArrayEquals(singleTrueArray, Bools.concat(emptyArray, singleTrueArray), "emptyArray + {true} should remain {true}"),
                () -> assertArrayEquals(singleFalseArray, Bools.concat(emptyArray, singleFalseArray), "emptyArray + {false} should remain {false}")
        );
    }

}
