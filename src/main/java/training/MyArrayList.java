package training;

import java.util.*;

/**
 * This class represents self-made implementation several methods of the List interface
 * @author kara.vladimir2@gmail.com.
 */
public class MyArrayList<E> extends AbstractList<E> {
    /**
     * default capacity
     */
    private static final int DEFAULT_CAPACITY = 8;
    /**
     * Array that stores all elements.
     */
    private Object[] elements;
    /**
     * size of list
     */
    private int size;


    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    public MyArrayList(int size) {
        if (size >= 0) {
            this.elements = new Object[size];
        }
    }

    /**
     * This method initiates MyArrayList of other Collection
     * @param collection
     */
    public MyArrayList(Collection<? extends E> collection) {
        elements = collection.toArray();
    }

    /**
     * Return true if add element to list successful
     * @param e
     * @return
     */
    @Override
    public boolean add(E e) {
        growIfNecessary(size + 1);
        elements[size++] = e;
        return true;
    }

    private void growIfNecessary(int requiredCapacity) {
        if (requiredCapacity > size) {
            int oldCapacity = elements.length;
            int newCapacity = oldCapacity * 3 / 2 + 1;
            if (newCapacity < oldCapacity) throw new Error("Overflow counter for list");
            newCapacity = Math.max(newCapacity, requiredCapacity);
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    /**
     * returns true if addition Collection to list was successful
     * @param index
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Object[] es = c.toArray();
        int reqQty = es.length;
        growIfNecessary(size+reqQty);
        System.arraycopy(es,0,elements,size,reqQty);
        size = size + reqQty;
        return true;
    }

    /**
     * add element to list at specified position
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index:"+index+" unappropriated");
        growIfNecessary(size+1);
        System.arraycopy(elements,index,elements,index+1,size-index);
        elements[index] = element;
        size++;
    }


    /**
     * inserts element to the list at specified position and returns old value
     * @param index
     * @param element
     * @return
     */
    @Override
    public E set(int index, E element) {
        checkBounds(index);
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }

    /**
     * returns the element at the specified position at the list
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        checkBounds(index);
        return (E) elements[index];
    }

    /**
     * clears list
     */
    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * removes and returns the element at the specified position
     * @param index
     * @return
     */
    @Override
    public E remove(int index) {
        checkBounds(index);
        E oldValue = (E) elements[index];
        removeEl(index);
        return oldValue;
    }

    /**
     * removes first occurrence of the specified element
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        if (Objects.isNull(o)) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    removeEl(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elements[i] == o) {
                    removeEl(i);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr();
    }

    private void removeEl(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elements, index+1, elements, index,
                    numMoved);
        elements[--size] = null;
    }


    private void checkBounds(int index) {
        if(index>=size)
            throw new IndexOutOfBoundsException("Index:" + index + " unappropriated");
    }

    private class ListItr implements ListIterator<E> {
        private int currPosition;
        private int nextPosition;

        @Override
        public boolean hasNext() {
            return currPosition!=size;
        }

        @Override
        public E next() {
            int i = nextPosition;
            Object[] objects = MyArrayList.this.elements;
            if (i>=size) throw new NoSuchElementException();
            nextPosition = i + 1;
            return (E) objects[currPosition = i];
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

        }
    }

}
