import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class DIYarrayList<T> implements List {
    public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    protected transient int modCount = 0;
    private static final Object[] emptyDefaultData = {};
    private static final Object[] defaultCapacityData = {};
    private static int defaultCapacity = 20;
    private static int size;
    ArrayList<Integer> arrayList = new ArrayList<>();

    private Object [] data;

    public DIYarrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.data = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.data = emptyDefaultData;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }
    public DIYarrayList() {
        this.data = defaultCapacityData;
    }
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void replaceAll(UnaryOperator operator) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public void sort(Comparator c) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(0) >= 0;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }


    @Override
    public Object[] toArray(Object[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(data, size, a.getClass());
        System.arraycopy(data, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("No such element in DIYarrayList on index: " +index);
    }

    private void rangeCheckForRemove(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("No such element in DIYarrayList on index: " +index);
    }

    public void add(int index, Object element) {
        rangeCheckForAdd(index);
        modCount++;
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.data).length)
            elementData = grow();
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        data[index] = element;
        size = s + 1;
    }

    @SuppressWarnings("unchecked")
    public void add(Object e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = (T)e;
        size = s + 1;
    }

    @SuppressWarnings("unchecked")
    public boolean add(Object o) {
        modCount++;
        add((T)o, data, size);
        return true;
    }

    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(data, index+1,data, index,
                    numMoved);
        data[--size] = null; // clear to let GC do its work
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (data[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(data[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    @Override
    public void forEach(Consumer action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final T[] data = (T[]) this.data;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            action.accept(data[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    public static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        // assert oldLength >= 0
        // assert minGrowth > 0

        int newLength = Math.max(minGrowth, prefGrowth) + oldLength;
        if (newLength - MAX_ARRAY_LENGTH <= 0) {
            return newLength;
        }
        return hugeLength(oldLength, minGrowth);
    }

    private static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) { // overflow
            throw new OutOfMemoryError("Required array length too large");
        }
        if (minLength <= MAX_ARRAY_LENGTH) {
            return MAX_ARRAY_LENGTH;
        }
        return Integer.MAX_VALUE;
    }

    private Object[] grow(int minCapacity) {
        int oldCapacity = data.length;
        System.out.println(oldCapacity);
        if (oldCapacity > 0 || data != defaultCapacityData) {
            int newCapacity = newLength(oldCapacity,
                    minCapacity - oldCapacity, /* minimum growth */
                    oldCapacity >> 1           /* preferred growth */);
            return data = Arrays.copyOf(data, newCapacity);
        } else {
            System.out.println(minCapacity + " "+ oldCapacity+ " "+ defaultCapacity);
            return data = new Object[Math.max(defaultCapacity, minCapacity)];
        }
    }

    private Object[] grow() {
        return grow(size + 1);
    }

   T data(int index) {
        return (T) data[index];
    }

    @Override
    public T remove(int index) {
        rangeCheckForRemove(index);

        modCount++;
        T oldValue = data(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(data, index+1, data, index,
                    numMoved);
        data[--size] = null; // clear to let GC do its work
        return oldValue;
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public void clear() {
        modCount++;

        // clear to let GC do its work
        for (int i = 0; i < size; i++)
            data[i] = null;

        size = 0;
    }

    @Override
    public Object get(int index) {
        return data[index] ;
    }

    @Override
    public Object set(int index, Object element) {
        data[index] = element;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfRange(o,0, size);
    }

    int indexOfRange(Object o, int start, int end) {
        Object[] es = data;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (data[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(data[i]))
                    return i;
        }
        return -1;
    }

    public int lastIndexOf() {
        int i;
        if (this == null) {
            for (i = size - 1; i >= 0; i--)
                if (data[i] == null)
                    return i;
        } else {
            for ( i = size - 1; i >= 0; i--)
                if (this.get(i).equals(data[i])) {
                    return i;
                }
        }
        return i;
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public Spliterator spliterator() {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }
}
