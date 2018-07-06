import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private Object o;

    @BeforeAll
    static void setUpClass() {
        System.out.println("setup class");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("setup");
        o = new Object();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDown");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("tearDownClass");
    }

    @Test
    void ok() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void failure() {
        assertEquals(2, 1 + 1);
//        assertEquals(3, 1 + 1);
    }

    @Test
    void error() {
//        throw new RuntimeException();
    }

    @Test
    void test() {
        System.out.println("test");
        assertEquals(2, 1 + 1, "Invalid sum");
        assertNotEquals(2, 3, "asdf");
        assertFalse(false, "msg");
        assertTrue(true, "lol");
        assertNull(null, "msg");
        assertNotNull(o, "msg");
    }
}
