package edu.emory.cs.queue;

import java.util.*;

public class LazyPriorityQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T>{

    private final List<T> keys;

    // defines this lazy pq as max pq order
    public LazyPriorityQueue(){
        this(Comparator.naturalOrder());
    }

    /** @see AbstractPriorityQueue#AbstractPriorityQueue(Comparator) */
    public LazyPriorityQueue(Comparator<T> priority){
        super(priority);
        keys = new ArrayList<>();
    }

    //overrides abstract priority queue parent class's add method, now returns size of keys arraylist instead
    @Override
    public int size() {
        return keys.size();
    }

    // appends key to arraylist {@link #keys}
    // lazy pq doesn't worry about adding in order, just adds to list
    /** @param key the key to be added */
    @Override
    public void add(T key){
        keys.add(key);
    }

    // removes key with highest/lowest priority from arraylist {@link #keys}
    // lazy pq has to iterate through keys and find the key with highest/ lowest order now since it's not sorted
    // remove doesn't need key parameter bc it will still always remove the highest/lowest priority first
    @Override
    public T remove(){
        if(isEmpty()) return null;
        T max = Collections.max(keys, priority);
        keys.remove(max);
        return max;
    }
}
