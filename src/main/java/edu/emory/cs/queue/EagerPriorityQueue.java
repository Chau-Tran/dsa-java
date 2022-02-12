package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EagerPriorityQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T>{

    private final List<T> keys;

    // defines this eager pq as max pq order
    public EagerPriorityQueue(){
        this(Comparator.naturalOrder());
    }

    /** @see AbstractPriorityQueue#AbstractPriorityQueue(Comparator) */
    public EagerPriorityQueue(Comparator<T> priority){
        super(priority);
        keys = new ArrayList<>();
    }

    //overrides abstract priority queue parent class's add method, now returns size of keys arraylist instead
    @Override
    public int size() {
        return keys.size();
    }

    // appends key to arraylist {@link #keys}
    // eager pq adds in a sorted order first
    /** @param key the key to be added */
    @Override
    public void add(T key){
        int index = Collections.binarySearch(keys, key, priority); //binary search, if not found, returns index < 0, and the correct index is {@code -(index+1)}
        if(index<0) index = - (index+1);
        keys.add(index, key);
    }

    // removes key with highest/lowest priority from arraylist {@link #keys}
    // lazy pq does not have to iterate through keys and find the key with highest/ lowest order now since it's already sorted
    // remove doesn't need key parameter bc it will still always remove the highest/lowest priority first
    @Override
    public T remove(){
        return isEmpty() ? null : keys.remove(keys.size() - 1);
    }
}
