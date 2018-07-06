import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {

    @Test
    public void testAdd() {
        ArrayList l = new ArrayList();

        assertTrue(l.isEmpty(), "List isn't empty");

        Object o = new Object();

        l.add(o);

        assertFalse(l.isEmpty(), "List is empty after insertion of an element");

        assertNotNull(l.get(0), "Something is wrong");
        assertNotEquals(new Object(), l.get(0), "Something is wrong");
        assertEquals(o, l.get(0), "Something is wrong");

        assertEquals(1, l.size(), "It didn't add just one object");

        assertTrue(l.add(null), "Didn't accepted null object");

        assertNull(l.get(1), "Something is wrong");
        assertNotEquals(new Object(), l.get(1), "Something is wrong");
        assertEquals(null, l.get(1), "Something is wrong");

        assertEquals(2, l.size(), "It didn't add just one object");

    }
}
