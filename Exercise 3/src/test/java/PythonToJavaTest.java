import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PythonToJavaTest {

    @Test
    void verifyAnswer() {

        PythonToJava py = new PythonToJava();

        boolean[] sieve = new boolean[1000];
        for (int i = 2; i < 1000; i ++) sieve[i] = true;

        for (int i = 2; i < 1000; i ++)
            for (int j = 2; i * j < 1000; j ++)
                sieve[i*j] = false;

        ArrayList<Integer> s = new ArrayList<>();
        for (int i = 0; i < 1000; i ++) s.add(i);

//        for (int i = 0; i < 1000; i ++)
//            assertTrue(sieve[i] == py.isPrime(i), i + " should" + (sieve[i] ? " not" : "") + " be prime");
//        assertAll(s.stream().map({assertTrue(sieve[it] == py.isPrime(it), it + " should" + (sieve[it] ? " not" : "") + " be prime")}));
        assertAll(
//                Arrays.asList(s).stream().map(() -> i -> assertEquals(sieve[i], py.isPrime(i), i + " should" + (!sieve[i] ? " not" : "") + " be prime"));
                () -> {
                    for (int i = 0; i < 1000; i ++) {
                        assertEquals(sieve[i], py.isPrime(i), i + " should" + (!sieve[i] ? " not" : "") + " be prime");
                    }
                }
        );
    }
}
