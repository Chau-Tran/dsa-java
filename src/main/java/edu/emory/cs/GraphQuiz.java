/*
 * Copyright 2020 Emory University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @author Jinho D. Choi */
public class GraphQuiz extends Graph {
    public GraphQuiz(int size) {
        super(size);
    }

    public GraphQuiz(Graph g) {
        super(g);
    }

    List<List<Integer>> canonCycles = new ArrayList<>();
    List<List<Integer>> pathCycles = new ArrayList<>();

    /**
     * @return the total number of cycles in this graph.
     */
    public int numberOfCycles() {

        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));

        while (!notVisited.isEmpty()) {
            cycleAux(notVisited.poll(), notVisited, new HashSet<>(), new ArrayList<>());
        }

        return canonCycles.size();
    }

    public void cycleAux(int target, Deque<Integer> notVisited, Set<Integer> visited, List<Integer> path) {

        notVisited.remove(target);
        visited.add(target);
        path.add(target);

        for (Edge edge : getIncomingEdges(target)) {

            if (visited.contains(edge.getSource())) {

                int root = path.indexOf(edge.getSource());
                List<Integer> canon = new ArrayList<>();
                canon.add(edge.getSource());

                for (int i = path.size() - 1; i > root; i--) canon.add(path.get(i));

                List<Integer> pth = List.copyOf(canon);
                canon.sort(Comparator.naturalOrder());

                if (!canonCycles.contains(canon)) {
                    canonCycles.add(canon);
                    pathCycles.add(pth);
                } else {
//                    System.out.println("canon cycle of pth alr in cycles: " + pth.toString() + canonCycles.toString());
                    List<Integer> cmp = pth.stream().skip(1).collect(Collectors.toList());
                    cmp.add(pth.get(0));
//                    System.out.println("cmp: " + cmp.toString());

                    if (!pathCycles.contains(cmp)) {
//                        System.out.println("pth is not same as canon");
                        canonCycles.add(canon);
                        pathCycles.add(pth);
                    }
                }
            } else {
                cycleAux(edge.getSource(), notVisited, new HashSet<>(visited), new ArrayList<>(path));
            }
//            System.out.println(canonCycles.toString());
        }
    }
}