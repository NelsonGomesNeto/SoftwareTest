package br.ufal.ic.exercise1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BasicTests {

    @Test
    void arrayEquality() {

        int[] expected = new int[] {1, 2, 3};
        int[] actual = new int[] {1, 2, 3};
        assertArrayEquals(expected, actual, "Arrays are different");
    }

    @Test
    void objectEquality() {

        Object expected = new Object();
        Object actual = expected;
        assertEquals(expected, actual, "Objects are different");
    }

    @Test
    void objectInequality() {

        Object expected = new Object();
        Object actual = new Object();
        assertNotEquals(expected, actual, "Objects aren't different");
    }

    @Test
    void stringContains() {

        String base = "computação";
        assertAll("String doesn't contains 'o' and 'ta'",
                () -> assertTrue(base.contains("o"), "String doesn't contains 'o'"),
                () -> assertTrue(base.contains("ta"), "String doesn't contains 'ta'"));
    }

    @Test
    void listHasAtLeastAnd() {

        List<String> base = Stream.of("um", "dois", "três", "quatro").collect(Collectors.toList());
        assertAll("List doesn't contains 'dois' and 'quatro'",
                () -> assertTrue(base.contains("dois"), "String doesn't contains 'dois'"),
                () -> assertTrue(base.contains("quatro"), "String doesn't contains 'quatro'"));
    }

    @Test
    void stringIsComputacao() {

        String base = "computação";
        String expected = "computação";
        String begin = "comput";
        assertAll("String isn't equals to \"computação\" or doesn't starts with \"comput\"",
                () -> assertTrue(base == expected, "String isn't equals to \"computação\""),
                () -> assertTrue(base.startsWith(begin), "String doesn't starts with \"comput\""));
    }

    @Test
    void stringIsntMathOrPhysics() {

        String base = "instituto";
        String expectedMath = "matemática";
        String expectedPhysics = "física";
        assertAll("String is equal to \"matemática\" or is equal to \"física\"",
                () -> assertTrue(base != expectedMath, "String is equal to \"matemática\""),
                () -> assertTrue(base != expectedPhysics, "String is equal to \"física\""));
    }

    @Test
    void stringIsMathOrInstitute() {

        String base = "instituto";
        String expectedMath = "matemática";
        String expectedInstitute = "instituto";
        // How would I do this with lambda expressions?
        assertTrue(base == expectedMath || base == expectedInstitute, "String isn't equal to \"matemática\" and isn't equal to \"instituto\"");
    }

    @Test
    void sevenIsntThreeOfFour() {

        int seven = 7, three = 3, four = 4;
        assertAll("7 is equal to 3 or 4",
                () -> assertTrue(seven != three, "7 is equal to 3"),
                () -> assertTrue(seven != four, "7 is equal to 4"));
    }

    @Test
    void objectInstanceIsDifferent() {

        Object obj1 = new Object(), obj2 = new Object();
        assertNotEquals(obj1, obj2, "Instances are equal");
    }

    @Test
    void everyStringsContainsA() {

        ArrayList<String> listOfStrings = new ArrayList<String>(Arrays.asList("miau", "avião"));
//        listOfStrings.forEach(e -> {
//            assertTrue(e.contains("a"), "Not every item contains letter 'a'");
//        });
        assertTrue(listOfStrings.stream().allMatch(e -> e.contains("a")), "Not every item contains letter 'a'");
    }
}
