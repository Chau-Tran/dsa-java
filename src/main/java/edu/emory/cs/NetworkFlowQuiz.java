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
package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

/** @author Jinho D. Choi */
public class NetworkFlowQuiz {
    /**
     * Using depth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    Set<Subgraph> paths = new HashSet<>();

    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Subgraph subgraph = new Subgraph();
        augAux(target, source, subgraph, graph);
        return paths;
    }

    public void augAux(int target, int source, Subgraph subgraph, Graph graph){

        if (source == target) paths.add(subgraph);

        for (Edge edge : graph.getIncomingEdges(target)) {

            if (subgraph.contains(edge.getSource())) continue;
            Subgraph tmp = new Subgraph(subgraph);
            tmp.addEdge(edge);
            augAux(edge.getSource(), source, tmp, graph);
        }
    }

//    public static void main(String[] args) {
//        NetworkFlowQuiz net = new NetworkFlowQuiz();
//        Graph graph = new Graph(4);
//        int s = 0, t = 3;
//
//        graph.setDirectedEdge(2, t, 1);
//        graph.setDirectedEdge(1, t, 1);
//        graph.setDirectedEdge(1, 2, 1);
//        graph.setDirectedEdge(s, 2, 1);
//        graph.setDirectedEdge(s, 1, 1);
//
//        for (Subgraph sub : net.getAugmentingPaths(graph, s, t)){
//            System.out.println(sub.getEdges());
//        }
//
//    }
}
