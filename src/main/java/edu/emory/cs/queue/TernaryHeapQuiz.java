package edu.emory.cs.queue;

import java.util.*;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    @Override
    public int size() {
        return keys.size() - 1;
    }

    private int compare(int k1, int k2){
        return priority.compare(keys.get(k1), keys.get(k2));
    }

    private int compare3(int k1, int k2, int k3){
        int max = k1;
        if(compare(max, k2) < 0) max = k2;
        if(compare(max, k3) < 0) max = k3;
        return max;
    }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    private void swim(int child){
        for (; 1 < child && compare((child+1)/3, child) < 0; child = (child+1)/3)
            Collections.swap(keys, (child+1)/3, child);
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;

        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    private void sink(){
            for(int k = 1, i = 2; i < size(); k = i, i = (i * 3)-1) { // k = parent, i = left child; compare while child index is <= size or no more children to compare to; traverse: parent index becomes child index, child index becomes next left child

                if (i+1 <= size() && compare(i, i + 1) < 0) { i++;
                    if (i+1 < size() && compare(i, i + 1) < 0) {
                        i++;
                    }
                }
                else if (i+2 < size() && compare(i, i + 2) < 0) i += 2;

                if (compare(k, i) >= 0) break; // if parent is already bigger than child, break
                Collections.swap(keys, k, i); // else, swap parent and child

            }
    }
}
