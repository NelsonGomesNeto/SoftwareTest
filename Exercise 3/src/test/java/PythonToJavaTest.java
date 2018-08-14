import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

//import java.lang.reflect.Executable;
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
        int sieveSize = 10001;

        boolean[] sieve = new boolean[sieveSize];
        for (int i = 2; i < sieveSize; i ++) sieve[i] = true;

        for (int i = 2; i < sieveSize; i ++)
            for (int j = 2; i * j < sieveSize; j ++)
                sieve[i*j] = false;

        ArrayList<Executable> executables = new ArrayList<>();
        for (int j = 0; j < sieveSize; j ++) {
            final int i = j;
            executables.add(()->assertEquals(sieve[i], py.isPrime(i), i + " should" + (!sieve[i] ? " not" : "") + " be prime"));
        }

        assertAll("Not working for all primes.",
                executables
        );
    }
}
