package edu.emory.cs.sort.distribution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RadixSortQuiz<T> extends RadixSort {

    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {

        int maxBit = getMaxBit(array, beginIndex, endIndex);

        Deque<Integer> arr = new LinkedList<Integer>();
        Deque<Integer> sorted = new LinkedList<Integer>();

        for (Integer i : array) {
            arr.add(i);
        }

        sortMSD(arr, beginIndex, endIndex, maxBit, sorted);

        for(int i = beginIndex; i < endIndex; i++ ){
            array[i] = sorted.remove();
        }
    }

    protected Deque<Integer> sortMSD(Deque<Integer> bucket, int beginIndex, int endIndex, int bit,Deque<Integer> sorted) {
        List<Deque<Integer>> buckets = Stream.generate(ArrayDeque<Integer>::new).limit(10).collect(Collectors.toList());

        int div = (int) Math.pow(10, bit - 1);
        if (div == 0) return sorted;
        bit--;

        for (int i = beginIndex; i < endIndex; i++) {
            int bucketIndex = (((int) bucket.peek()) / div) % 10;
            buckets.get(bucketIndex).add(bucket.remove());
        }

        for (Deque<Integer> buck : buckets) {
             if (buck.size() > 1 ) {
                sortMSD(buck, 0, buck.size(), bit, sorted);
            }
             while(!buck.isEmpty()){
                 sorted.add(buck.remove());
             }
        }
        return sorted;
    }
}