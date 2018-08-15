package br.ufal.ic.atividades.teste;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;

public class BooleanArrayAsList extends AbstractList<Boolean>
        implements RandomAccess, Serializable {

    boolean[] array; // was final
    final int start;
    final int end;
    final Bools bools;

    public BooleanArrayAsList(boolean[] array, Bools bools) {
        this(array, 0, array.length, bools);
    }

    public BooleanArrayAsList(boolean[] array, int start, int end, Bools bools) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.bools = bools;
    }

    @Override
    public int size() {
        return end - start;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0; /*false*/
    }

    @Override
    public Boolean get(int index) {
        bools.checkElementIndex(index, size(), "index");
        return array[start + index];
    }

    @Override
    public boolean contains(Object target) {
        // Overridden to prevent a ton of boxing
        return (target instanceof Boolean)
                && bools.indexOf(array, (Boolean) target) >= 0 /*was -1 but it only specifies < 0*/;
    }

    @Override
    public int indexOf(Object target) {
        // Overridden to prevent a ton of boxing
        if (target instanceof Boolean) {
            int i = bools.indexOf(array, (Boolean) target);
            if (i >= 0) {
                return i - start;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object target) {
        // Overridden to prevent a ton of boxing
        if (target instanceof Boolean) {
            int i = bools.lastIndexOf(array, (Boolean) target);
            if (i >= 0) {
                return i - start /*+ 1*/;
            }
        }
        return -1;
    }

    @Override
    public Boolean set(int index, Boolean element) {
        bools.checkElementIndex(index, size(), "index");
        boolean oldValue = array[start + index];
        // checkNotNull for GWT (do not optimize)
        array[start + index] = bools.checkNotNull(element);
        return oldValue;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof BooleanArrayAsList) {
            BooleanArrayAsList that = (BooleanArrayAsList) object;
            int size = size();
            if (that.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (array[start + i] != that.array[that.start + i]) {
                    return false;
                }
            }
            return true;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = start; i < end; i++) {
            result = 31 * result + Boolean.hashCode(array[i]);
        }
        return result;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
        	return "[]";
        } // Didn't handle empty arrays
        StringBuilder builder = new StringBuilder(size() * 7);
        builder.append(array[start] ? "[true" : "[false");
        for (int i = start + 1; i < end; i++) {
            builder.append(array[i] ? ", true" : ", false");
        }
        return builder.append(']').toString();
    }

    boolean[] toBooleanArray() {
        return Arrays.copyOfRange(array, start, end);
    }

    private static final long serialVersionUID = 0;
}
