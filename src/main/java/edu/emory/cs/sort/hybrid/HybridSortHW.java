package edu.emory.cs.sort.hybrid;
import edu.emory.cs.sort.divide_conquer.QuickSort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HybridSortHW<T extends Comparable<T>> extends QuickSort implements HybridSort<T> {

    final Comparator<T> comparator = Comparator.naturalOrder();

    @Override
    public T[] sort(T[][] input) {
        for (T[] arr : input) sortArr(arr);
        return merge(input);
    }

    public int ascend(T[] arr) {
        int n = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                n = n + 1;
            }
        }
        if (n == 0) return 1;
        else if (n <= arr.length / 4) return 3; //ascending
        else return 5;//random
    }
    public int descend(T[] arr) {
        int n = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (comparator.compare(arr[i], arr[i + 1]) < 0) {
                n++;
            }
        }
        if (n == 0) return 2;
        else if (n <= arr.length / 4) return 4;
        else return 5;
    }
    public void sortArr(T[] arr) {
        int asc = ascend(arr);
        if (asc == 1) {
        } else if (asc == 3) {
            for (int i = 1; i < arr.length; ++i) {
                T key = arr[i];
                int j = i - 1;

                while (j >= 0 && comparator.compare(arr[j], key) > 0) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                }
                arr[j + 1] = key;
            }
        } else if (asc == 5) {
            int des = descend(arr);
            if (des == 2 | des == 4) {
                T tmp;
                for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
                if (des == 4) {
                    sortArr(arr);
                }
            } else if (des == 5) {
                sort(arr, 0, arr.length);
            }
        }
    }

    public class Node implements Comparable<Node> {
        private final int arrayNum;
        private final T val;
        private final int ind;

        public Node(int arrayNum, int ind, T val) {
            this.arrayNum = arrayNum;
            this.ind = ind;
            this.val = val;
        }

        @Override
        public int compareTo(Node o) {
            return comparator.compare(o.val, this.val);
        }
    }
    public T[] merge(T[][] input) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < input.length; i++) {
            Node n = new Node(i, 0, input[i][0]);
            pq.add(n);
        }
        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), size);
        int i = 0;
        while (!pq.isEmpty()) {
            Node n = pq.remove();
            if (n != null) {
                output[i++] = n.val;
                if (n.ind + 1 < input[n.arrayNum].length) {
                    Node nw = new Node(n.arrayNum, n.ind + 1, input[n.arrayNum][n.ind + 1]);
                    pq.add(nw);
                }
            }
        }
        return output;
    }
}