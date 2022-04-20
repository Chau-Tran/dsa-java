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
package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.*;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {

    List<SpanningTree> mst = new ArrayList<>();
    List<List<String>> canonTrees = new ArrayList<>();

    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        if (graph.getAllEdges().size() == 0) {
            System.out.println(mst.size());
            return mst;
        }
        mstAux(0, graph, new PriorityQueue<>(), new SpanningTree(), new HashSet<>());
        System.out.println(mst.size());

//        MSTPrim min = new MSTPrim();
//        mst.add(min.getMinimumSpanningTree(graph));
//        System.out.println(mst);

        return mst;

    }

    public List<String> redundant(SpanningTree t1) {
        List<String> canon = new ArrayList<>();

        for (Edge e : t1.getEdges()) {
            String str = new String();

            if (e.getSource() > e.getTarget()) {
                str += e.getTarget() + " - " + e.getSource();
            } else {
                str += e.getSource() + " - " + e.getTarget();
            }
            canon.add(str);
        }
        Collections.sort(canon);
        return canon;
    }

    public void mstAux(int vertex, Graph graph, PriorityQueue<Edge> queue, SpanningTree tree, HashSet<Integer> visited) {

        add(queue, visited, graph, vertex);
        List<Edge> diverges = new ArrayList<>();

        double min;
        if(!queue.isEmpty()) min = queue.peek().getWeight();
        else return;

        for (Edge e : queue) {
            if (e.getWeight() == min) diverges.add(e);
        }

        for (Edge e : diverges) {

            PriorityQueue<Edge> altQ = new PriorityQueue<>(queue);
            altQ.remove(e);

            if (!visited.contains(e.getSource())) {
                SpanningTree altTree = new SpanningTree(tree);
                altTree.addEdge(e);
                if (altTree.size() + 1 == graph.size()) {
                    if (!canonTrees.contains(redundant(altTree))) {
                        canonTrees.add(redundant(altTree));
                        mst.add(altTree);
                    }
                }
                mstAux(e.getSource(), graph, altQ, altTree, new HashSet<>(visited));
            }
        }
    }

    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);
        for (Edge edge : graph.getIncomingEdges(target)) {
            if (!visited.contains(edge.getSource()))
                queue.add(edge);
        }
    }
}