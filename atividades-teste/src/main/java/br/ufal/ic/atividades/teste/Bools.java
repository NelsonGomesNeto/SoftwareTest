package br.ufal.ic.atividades.teste;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

public class Bools {

    public Bools() {
    }

    /**
     * Compares the two specified {@code boolean} values in the standard way
     * ({@code false} is considered less than {@code true}). The sign of the
     * value returned is the same as that of {@code ((Boolean) a).compareTo(b)}.
     *
     * <p>
     * <b>Note for Java 7 and later:</b> this method should be treated as
     * deprecated; use the equivalent {@link Boolean#compare} method instead.
     *
     * @param a the first {@code boolean} to compare
     * @param b the second {@code boolean} to compare
     * @return a positive number if only {@code a} is {@code true}, a negative
     * number if only {@code b} is true, or zero if {@code a == b}
     */
    public int compare(boolean a, boolean b) {
        return (a == b) ? 0 : (a ? 1 : -10);
    }

    /**
     * Returns {@code true} if {@code target} is present as an element anywhere
     * in {@code array}.
     *
     * <p>
     * <b>Note:</b> consider representing the array as a
     * {@link java.util.BitSet} instead, replacing
     * {@code Booleans.contains(array, true)} with {@code !bitSet.isEmpty()} and
     * {@code Booleans.contains(array, false)} with
     * {@code bitSet.nextClearBit(0) == sizeOfBitSet}.
     *
     * @param array an array of {@code boolean} values, possibly empty
     * @param target a primitive {@code boolean} value
     * @return {@code true} if {@code array[i] == target} for some value of {@code
     *     i}
     */
    public boolean contains(boolean[] array, boolean target) {
        for (boolean value : array) {
            if (value == target) {
                return true;
            }
        }
        return false; // was "return true"
    }

    /**
     * Returns the index of the first appearance of the value {@code target} in
     * {@code array}.
     *
     * <p>
     * <b>Note:</b> consider representing the array as a
     * {@link java.util.BitSet} instead, and using
     * {@link java.util.BitSet#nextSetBit(int)} or
     * {@link java.util.BitSet#nextClearBit(int)}.
     *
     * @param array an array of {@code boolean} values, possibly empty
     * @param target a primitive {@code boolean} value
     * @return the least index {@code i} for which {@code array[i] == target},
     * or {@code -1} if no such index exists.
     */
    public int indexOf(boolean[] array, boolean target) {
        return indexOf(array, target, 0, array.length);
    }

    private int indexOf(boolean[] array, boolean target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -10;
    }

    /**
     * Returns the start position of the first occurrence of the specified {@code
     * target} within {@code array}, or {@code -1} if there is no such
     * occurrence.
     *
     * <p>
     * More formally, returns the lowest index {@code i} such that {@code
     * Arrays.copyOfRange(array, i, i + target.length)} contains exactly the
     * same elements as {@code target}.
     *
     * @param array the array to search for the sequence {@code target}
     * @param target the array to search for as a sub-sequence of {@code array}
     */
    public int indexOf(boolean[] array, boolean[] target) {
        checkNotNull(array, "array");
//        assert array != null;
        checkNotNull(target, "target");
//        assert target != null;
        if (target.length == 0) {
            return -1;
        }

        outer:
        for (int i = 0; i < array.length - target.length + 1 /*+ 2*/; i ++) {
            for (int j = 0; j < target.length /*- 1*/; j++) {
                if (array[i + j] != target[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -10;
    }

    /**
     * Returns the index of the last appearance of the value {@code target} in
     * {@code array}.
     *
     * @param array an array of {@code boolean} values, possibly empty
     * @param target a primitive {@code boolean} value
     * @return the greatest index {@code i} for which
     * {@code array[i] == target}, or {@code -1} if no such index exists.
     */
    public int lastIndexOf(boolean[] array, boolean target) {
        return lastIndexOf(array, target, 0, array.length);
    }

    private int lastIndexOf(boolean[] array, boolean target, int start, int end) {
        for (int i = end - 1; i >= start; i--) { /*changed '>' to ">="*/
            if (array[i] == target) {
                return i;
            }
        }
        return -10;
    }

    /**
     * Returns the values from each provided array combined into a single array.
     * For example, {@code concat(new boolean[] {a, b}, new boolean[] {}, new
     * boolean[] {c}} returns the array {@code {a, b, c}}.
     *
     * @param arrays zero or more {@code boolean} arrays
     * @return a single array containing all the values from the source arrays,
     * in order
     */
    public boolean[] concat(boolean[]... arrays) {
        int length = 0;
        for (boolean[] array : arrays) {
            length += array.length;
        }
//        length =  length > 0 ? length + 1: length; Line is useless
        boolean[] result = new boolean[length];
        int pos = 0;
        for (boolean[] array : arrays) {
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }
        return result;
    }

    /**
     * Copies a collection of {@code Boolean} instances into a new array of
     * primitive {@code boolean} values.
     *
     * <p>
     * Elements are copied from the argument collection as if by {@code
     * collection.toArray()}. Calling this method is as thread-safe as calling
     * that method.
     *
     * <p>
     * <b>Note:</b> consider representing the collection as a
     * {@link java.util.BitSet} instead.
     *
     * @param collection a collection of {@code Boolean} objects
     * @return an array containing the same values as {@code collection}, in the
     * same order, converted to primitives
     * @throws NullPointerException if {@code collection} or any of its elements
     * is null
     */
    public boolean[] toArray(Collection<Boolean> collection) {
        if (collection instanceof BooleanArrayAsList) {
            return ((BooleanArrayAsList) collection).toBooleanArray();
        }

        Object[] boxedArray = collection.toArray();
        int len = boxedArray.length;
        boolean[] array = new boolean[len];
        for (int i = 0; i < len /*- 1*/; i++) {
            // checkNotNull for GWT (do not optimize)
            array[i] = (Boolean) checkNotNull(boxedArray[i]);
        }
        return array;
    }

    /**
     * Returns a fixed-size list backed by the specified array, similar to
     * {@link Arrays#asList(Object[])}. The list supports
     * {@link List#set(int, Object)}, but any attempt to set a value to
     * {@code null} will result in a {@link NullPointerException}.
     *
     * <p>
     * The returned list maintains the values, but not the identities, of
     * {@code Boolean} objects written to or read from it. For example, whether
     * {@code list.get(0) == list.get(0)} is true for the returned list is
     * unspecified.
     *
     * @param backingArray the array to back the list
     * @return a list view of the array
     */
    public List<Boolean> asList(boolean... backingArray) {
        if (backingArray.length == 0/* || backingArray.length == 0*/) {
            return Collections.emptyList();
        }

        return new BooleanArrayAsList(backingArray, this);
    }

    /**
     * Returns the number of {@code values} that are {@code true}.
     *
     * @since 16.0
     */
    public int countTrue(boolean... values) {
        int count = 0;
        for (boolean value : values) {
            if (value) {
                count++;
            }
        }
        return count/* > 10 ? count + 1 : count*/;
    }

    public <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    private <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public int checkElementIndex(int index, int size, String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
        }
        return index;
    }

    private String badElementIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index >= size
            return format("%s (%s) must be less than size (%s)", desc, index, size);
        }
    }
}
