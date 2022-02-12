package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BinaryHeap <T extends Comparable<T>> extends AbstractPriorityQueue<T>{

    private final List<T> keys;

    // default constructor with natural order
    public BinaryHeap(){
        this(Comparator.naturalOrder());
    }

    /** @see AbstractPriorityQueue#AbstractPriorityQueue(Comparator)*/
    public BinaryHeap(Comparator<T> priority){
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    // new size method returns length of keys
    @Override
    public int size(){
        return keys.size() - 1;
    }

/** @param i1 the index of the first key in {@link #keys} */
/** @param i2 the index of the second key in {@link #keys} */
/** @return a negative int if k1 is less than k2, positive int if k1 is greater than k2, or zero if they're equal */
    private int compare(int k1, int k2){
        return priority.compare(keys.get(k1), keys.get(k2));
    }

    // adds key as a leaf and then continuously swims it up the tree until it fits
    public void add(T key){
        keys.add(key);
        swim(size()); //swims the newly added node up to its correct position
    }

    //repeatedly compares the new key with parent node and swaps parent and child if needed so order is restored
    private void swim(int k){
        for(; 1 < k && compare(k/2, k) < 0; k /= 2) //compares the parent and child and if child is bigger than parent, swaps them
            Collections.swap(keys, k/2, k);
    }

    @Override
    public T remove(){
        if(isEmpty()) return null;
        Collections.swap(keys, 1, size()); //swaps the root (highest/lowest value) with the last child (a leaf node)
        T max = keys.remove(size()); // removes the highest/lowest value (currently a leaf node) which avoids destroying the whole tree
        sink(); // restores tree order by bringing the last child (currently at the top) back down to its correct position
        return max;
    }

    // sink the child key back down to its correct position to restore tree's order
    private void sink(){
        for(int k = 1, i = 2; i <= size(); k = i, i*=2) { // k = parent, i = left child; compare while child index is <= size or no more children to compare to; traverse: parent index becomes child index, child index becomes i*2 which is the next child
            if (i < size() && compare(i, i + 1) < 0) i++; // while the child is still not at the end, compare the left child and right child to find the larger of the two numbers. If right is bigger, child index increases
            if (compare(k, i) >= 0) break; // if parent is already bigger than child, break
            Collections.swap(keys, k, i); // else, swap parent and child
        }
    }
}
