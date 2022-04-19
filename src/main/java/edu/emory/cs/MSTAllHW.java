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

    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {

        List<SpanningTree> trees = new ArrayList<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        SpanningTree spanningTree = new SpanningTree();
        Set<Integer> visited = new HashSet<>();

        double minWeight = new MSTPrim().getMinimumSpanningTree(graph).getTotalWeight(); // all mst should have this weight, any trees weighing more is not mst

        add(queue, visited, graph, 0);
        mstAux(graph, trees, queue, spanningTree, visited); // adds all spanning trees into trees list

        for (SpanningTree tree : trees) {

            HashSet<Integer> vertex = new HashSet<>();

            if (tree.getTotalWeight() != minWeight) continue; //not mst

            for (Edge edge : tree.getEdges()) {
                vertex.add(edge.getTarget());
                vertex.add(edge.getSource());
            }
            if (vertex.size() != graph.size()) continue; //not mst

            mst.add(tree); // passes conditions: is an mst!
        }
        return mst; // list of all mst's
    }

    public void mstAux(Graph graph, List<SpanningTree> trees, PriorityQueue<Edge> queue, SpanningTree spanningTree, Set<Integer> visited) {
        while (!queue.isEmpty()) {

            SpanningTree altTree = new SpanningTree(spanningTree);
            Set<Integer> altVisited = new HashSet<>(visited);

            List<Edge> edges = new ArrayList<>();
            Edge edge = queue.poll();
            edges.add(edge);

            PriorityQueue<Edge> altQ = new PriorityQueue<>(queue);

            while (!altQ.isEmpty()) {
                Edge altEdge = altQ.poll();
                if (edge.getWeight() == altEdge.getWeight()) edges.add(edge);
            }

            if (!visited.contains(edge.getSource())) {
                PriorityQueue<Edge> tempQ = new PriorityQueue<>(queue);
                altTree.addEdge(edge);

                if (altTree.size() + 1 == graph.size()) trees.add(altTree); //a spanning tree was found

                add(tempQ, altVisited, graph, edges.get(0).getSource());
                mstAux(graph, trees, tempQ, altTree, altVisited); // recurse
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

//    public static void main(String[] args) {
//        Graph graph = new Graph(4);
//        graph.setUndirectedEdge(0, 1, 2);
//        graph.setUndirectedEdge(1, 2, 2);
//        graph.setUndirectedEdge(2, 3, 2);
//        graph.setUndirectedEdge(3, 0, 2);
//
//        MSTAll gold = new MSTAllHW();
//        System.out.println("\n" + gold.getMinimumSpanningTrees(graph));
//    }
}

//    PriorityQueue<Edge> queue = new PriorityQueue<>();
//        SpanningTree tree = new SpanningTree();
//        Set<Integer> visited = new HashSet<>();
//        Edge edge;
//
//        add(queue, visited, graph, 0);
//
//        while(!queue.isEmpty()){
//
//            edge = queue.poll();
//
//            PriorityQueue<Edge> altQueue = new PriorityQueue<>(queue);
//            Edge alt = altQueue.peek();
//            altQueue.add(edge);
//
//
//            if(alt != null && edge.getWeight()== alt.getWeight()){
//
//                alt = altQueue.poll();
//
//                System.out.println("alt: " + alt + " altQ: " + altQueue);
//                System.out.println("edge: " + edge + " Q: " + queue);
//
//                mstAux(graph, edge,queue, new SpanningTree(), new HashSet<>(visited));
//                mstAux(graph, alt,altQueue, new SpanningTree(tree), new HashSet<>(visited));
//            }
//            else{
//                mstAux(graph, edge, queue, new SpanningTree(tree), new HashSet<>(visited));
//            }
//        }
//        return mst;
//    }
//
//    public void mstAux(Graph graph, Edge edge, PriorityQueue<Edge> queue, SpanningTree tree, HashSet<Integer> visited){
//        tree.addEdge(edge);
//        System.out.println("\nedge: " + edge + " ||| Q: " + queue + " ||| tree: " + tree + " ||| visited: " + visited);
//
//        add(queue, visited, graph, edge.getSource());
//        System.out.println("\nvisited: " + visited + " ||| queue: "+queue);
//
//
//        while(!queue.isEmpty()){
//
//            edge = queue.poll();
//
//            Edge alt = queue.peek();
//            PriorityQueue<Edge> altQueue = new PriorityQueue<>(queue);
//            altQueue.add(edge);
//
//            if(alt != null && edge.getWeight() == alt.getWeight()){
//                System.out.println("F");
//                alt = altQueue.poll();
//
//                mstAux(graph, edge, queue, new SpanningTree(tree), new HashSet<>(visited));
//                mstAux(graph, alt, altQueue, new SpanningTree(tree), new HashSet<>(visited));
//            }
//
//            else{
//                System.out.println("T");
//
//                if(!visited.contains(edge.getSource())){
//                    tree.addEdge(edge);
//                    if(tree.size() +1 == graph.size()){
//                        System.out.println("mst! " + tree);
//                        mst.add(tree);
//                        break;
//                    }
//                    add(queue, visited, graph, edge.getSource());
//                }
//                else if(tree.size() +1 == graph.size()){
//                    System.out.println("mst! " + tree);
//                    mst.add(tree);
//                    break;
//                }
//            }
//        }