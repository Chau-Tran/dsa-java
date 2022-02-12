package edu.emory.cs.queue;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityQueueTest {
    // @param pq a priority queue
    // @param keys a list of comparable keys
    // @param comp a comparator used for sorting, specifies if it's a max or min pq

    <T extends Comparable<T>> void testRobustness(AbstractPriorityQueue<T> pq, List<T> keys, Comparator<T> comp){
        keys.forEach(pq::add); // add each key in keys to pq
        keys = keys.stream().sorted(comp).collect(Collectors.toList()); // new keys list is a sorted version of the old keys list based on comparator comp
        keys.forEach(key -> assertEquals(key, pq.remove()));
    }

    @Test
    public void testRobustness(){ //3 types of max pqs sorted in reverse order to test the remove method and 3 min pqs sorted in natural order to test remove method
        List<Integer> keys = List.of(4,1,3,2,5,6,8,3,4,7,5,9,7);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new LazyPriorityQueue<>(), keys, reverse);
        testRobustness(new EagerPriorityQueue<>(), keys, reverse);
        testRobustness(new BinaryHeap<>(), keys, reverse);

        testRobustness(new LazyPriorityQueue<>(reverse), keys, natural);
        testRobustness(new EagerPriorityQueue<>(reverse), keys, natural);
        testRobustness(new BinaryHeap<>(reverse), keys, natural);
    }

    static class Time{ //stores runtime for add and remove methods
        long add = 0;
        long remove = 0;
    }

    <T extends Comparable<T>> void addRuntime(AbstractPriorityQueue<T> queue, Time time, List<T> keys){
        long st, et;

        //runtime for add
        st = System.currentTimeMillis(); // start time of adding
        keys.forEach(queue::add); // add all key in keys to pq
        et = System.currentTimeMillis(); // end time of adding
        time.add += et-st; // time elapsed

        //runtime for remove
        st = System.currentTimeMillis(); //start time of removing
        while(!queue.isEmpty()) queue.remove(); //while pq still has key to remove, remove all key from keys
        et = System.currentTimeMillis(); //end time of removing
        time.remove += et-st; //time elapsed
    }

    <T extends Comparable<T>> Time[] benchmark(AbstractPriorityQueue<T>[] queues, int size, int iter, Supplier<T> sup){
        Time[] times = Stream.generate(Time::new).limit(queues.length).toArray(Time[]::new); //an array of test times

        for(int i = 0; i < iter; i++){ //how many tests
            List<T> keys = Stream.generate(sup).limit(size).collect(Collectors.toList());  //generates list of keys specified by supplier
            for(int j = 0; j < queues.length; j++) //estimates runtime using same list of keys for each pq
                addRuntime(queues[j], times[j], keys);
        }
        return times;
    }

    @SafeVarargs
    final void testRuntime(AbstractPriorityQueue<Integer> ... queues){ //final method cannot be overriden by subclasses
        final int begin_size = 1000;
        final int end_size = 1000;
        final int inc = 1000;
        final Random rand = new Random(); //random generator

        for(int size = begin_size; size <= end_size; size += inc) { //tests different sizes of pqs
            benchmark(queues, size, 10, rand::nextInt);
            Time[] times = benchmark(queues, size, 1000, rand::nextInt);//tests add and remove methods

            StringJoiner joiner = new StringJoiner("\t");
            joiner.add(Integer.toString(size));
            joiner.add(Arrays.stream(times).map(t -> Long.toString(t.add)).collect(Collectors.joining("\t")));
            joiner.add(Arrays.stream(times).map(t -> Long.toString(t.remove)).collect(Collectors.joining("\t")));
            System.out.println(joiner.toString()); //prints results

        }
    }


}
