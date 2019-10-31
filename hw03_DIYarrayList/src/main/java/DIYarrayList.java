import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class DIYarrayList <T>  implements List <T> {
    public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    protected transient int modCount = 0;
    private static final Object[] emptyDefaultData = {};
    private static final Object[] defaultCapacityData = {};
    private static int defaultCapacity = 20;
    private int size;
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
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public void sort(Comparator<? super T> c) {
        List.super.sort(c);
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public T[] toArray() {
        return (T[]) Arrays.copyOf(data, size);
    }


    public void copy(List <T> dest, List <T> src){
        Collections.copy(dest,src);
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

    @SuppressWarnings("unchecked")
    private void add(Object e, Object[] elementData, int s) {
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
    public void forEach(Consumer<? super T> action) {
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
        if (oldCapacity > 0 || data != defaultCapacityData) {
            int newCapacity = newLength(oldCapacity,
                    minCapacity - oldCapacity, /* minimum growth */
                    oldCapacity >> 1           /* preferred growth */);
            return data = Arrays.copyOf(data, newCapacity);
        } else {
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
    public boolean addAll(Collection<? extends T> c) {
        return Collections.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return Collections.addAll(c);
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
    public T get(int index) {
        return (T) data[index] ;
    }

    @Override
    public Object set(int index, Object element) {
        data[index] = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
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
            for ( i = size - 1; i >= 0; i--){
                if (this.get(i).equals(data[i])) {
                    return i;
                }
            }
        return i;
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        rangeCheckForAdd(index);
        return (ListIterator<T>) new ListItr(index);
    }

    @Override
    public ListIterator<T> listIterator() {
        return (ListIterator<T>) new ListItr(0);
    }


    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    @Override
    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException("Such operation is not supported.");
    }

    private class SubList extends AbstractList<T> implements RandomAccess {
        private final AbstractList<T> parent;
        private final int parentOffset;
        private final int offset;
        int size;

        SubList(AbstractList<T> parent,
                int offset, int fromIndex, int toIndex) {
            this.parent = parent;
            this.parentOffset = fromIndex;
            this.offset = offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = DIYarrayList.this.modCount;
        }

        public T set(int index,T e) {
            rangeCheck(index);
            checkForComodification();
            T oldValue = DIYarrayList.this.data(offset + index);
            DIYarrayList.this.data[offset + index] = e;
            return oldValue;
        }

        public T get(int index) {
            rangeCheck(index);
            checkForComodification();
            return DIYarrayList.this.data(offset + index);
        }

        public int size() {
            checkForComodification();
            return this.size;
        }

        public Iterator<T> iterator() {
            return listIterator();
        }

        public ListIterator<T> listIterator(final int index) {
            checkForComodification();
            rangeCheckForAdd(index);
            final int offset = this.offset;

            return new ListIterator<T>() {
                int cursor = index;
                int lastRet = -1;
                int expectedModCount = DIYarrayList.this.modCount;

                public boolean hasNext() {
                    return cursor != SubList.this.size;
                }

                @SuppressWarnings("unchecked")
                public T next() {
                    checkForComodification();
                    int i = cursor;
                    if (i >= SubList.this.size)
                        throw new NoSuchElementException();
                    Object[] elementData = DIYarrayList.this.data;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i + 1;
                    return (T) elementData[offset + (lastRet = i)];
                }

                public boolean hasPrevious() {
                    return cursor != 0;
                }

                @SuppressWarnings("unchecked")
                public T previous() {
                    checkForComodification();
                    int i = cursor - 1;
                    if (i < 0)
                        throw new NoSuchElementException();
                    Object[] elementData = DIYarrayList.this.data;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i;
                    return (T) elementData[offset + (lastRet = i)];
                }

                @SuppressWarnings("unchecked")
                public void forEachRemaining(Consumer<? super T> consumer) {
                    Objects.requireNonNull(consumer);
                    final int size = SubList.this.size;
                    int i = cursor;
                    if (i >= size) {
                        return;
                    }
                    final Object[] elementData = DIYarrayList.this.data;
                    if (offset + i >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    while (i != size && modCount == expectedModCount) {
                        consumer.accept((T) elementData[offset + (i++)]);
                    }
                    // update once at end of iteration to reduce heap write traffic
                    lastRet = cursor = i;
                    checkForComodification();
                }

                public int nextIndex() {
                    return cursor;
                }

                public int previousIndex() {
                    return cursor - 1;
                }

                public void remove() {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        SubList.this.remove(lastRet);
                        cursor = lastRet;
                        lastRet = -1;
                        expectedModCount = DIYarrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void set(T e) {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        DIYarrayList.this.set(offset + lastRet, e);
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void add(T e) {
                    checkForComodification();

                    try {
                        int i = cursor;
                        SubList.this.add(i, e);
                        cursor = i + 1;
                        lastRet = -1;
                        expectedModCount = DIYarrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                final void checkForComodification() {
                    if (expectedModCount !=DIYarrayList.this.modCount)
                        throw new ConcurrentModificationException();
                }
            };
        }

        void subListRangeCheck(int fromIndex, int toIndex, int size) {
            if (fromIndex < 0)
                throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
            if (toIndex > size)
                throw new IndexOutOfBoundsException("toIndex = " + toIndex);
            if (fromIndex > toIndex)
                throw new IllegalArgumentException("fromIndex(" + fromIndex +
                        ") > toIndex(" + toIndex + ")");
        }

        public List<T> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size);
            return new SubList(this, offset, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+this.size;
        }

        private void checkForComodification() {
            if (DIYarrayList.this.modCount != this.modCount)
                throw new ConcurrentModificationException();
        }
    }

    static <T> T elementAt(Object[] es, int index) {
        return (T) es[index];
    }

    private class Itr implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        // prevent creating a synthetic constructor
        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                DIYarrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            final int size = DIYarrayList.this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = data;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < size && modCount == expectedModCount; i++)
                    action.accept(elementAt(es, i));
                // update once at end to reduce heap write traffic
                cursor = i;
                lastRet = i - 1;
                checkForComodification();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public T previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (T) elementData[lastRet = i];
        }
        @Override
        public void set(T e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                DIYarrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(T e) {
            checkForComodification();

            try {
                int i = cursor;
                DIYarrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
