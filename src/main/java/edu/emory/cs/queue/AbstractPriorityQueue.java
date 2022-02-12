package edu.emory.cs.queue;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<T extends Comparable<T>> {

    protected Comparator<T> priority;

    /** @param priority if {@link Comparator#naturalOrder()} max PQ
    //                     {@link Comparator#reverseOrder()} min PQ */

    public AbstractPriorityQueue(Comparator<T> priority) {
        this.priority = priority;
    }

    // adds a comparable key to pq
    /** @param key the key to be added */
    abstract public void add(T key);

    // removes the highest/lowest priority key
    /** @return the key with highest/lowest priority if it exists; otherwise null */
    abstract public T remove();

    // returns size if pq
    abstract public int size();

    // returns true if pq empty; otherwise false
    public boolean isEmpty(){
        return size() == 0;
    }

}
