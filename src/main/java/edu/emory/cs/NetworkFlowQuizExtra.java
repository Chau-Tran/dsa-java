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

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/** @author Jinho D. Choi */
public class NetworkFlowQuizExtra {
    /**
     * Using breadth-first traverse.
     * @param graph  a directed graph.
     * @param source the ource vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {

        Set<Subgraph> result = new HashSet<>();
        Queue<Subgraph> q = new ArrayDeque<>();

        for( Edge e : graph.getIncomingEdges(target)) {
            Subgraph tmp = new Subgraph();
            tmp.addEdge(e);
            if(source == target){
                result.add(tmp);
            }
            else {
                q.add(tmp);
            }
        }

        while(!q.isEmpty()){
            Subgraph sub = q.poll();
            Edge e = sub.getEdges().get(sub.getEdges().size()-1);
            System.out.println(e.toString());
            for(Edge incomingEdge : graph.getIncomingEdges(e.getSource())){
                if(sub.contains(incomingEdge.getSource())){
                    continue;
                }
                Subgraph tmp = new Subgraph(sub);
                tmp.addEdge(incomingEdge);
                if(source == incomingEdge.getSource()){
                    result.add(tmp);
                }
                else {
                    q.add(tmp);
                }
            }
        }
        System.out.println(result.size());
        return result;
    }
//    public static void main(String[] args) {
//        NetworkFlowQuizExtra net = new NetworkFlowQuizExtra();
//        Graph graph = new Graph(6);
//        int s = 0, t = 5;
//
//        graph.setDirectedEdge(s, 1, 4);
//        graph.setDirectedEdge(s, 2, 2);
//        graph.setDirectedEdge(1, 3, 3);
//        graph.setDirectedEdge(2, 3, 2);
//        graph.setDirectedEdge(2, 4, 3);
//        graph.setDirectedEdge(3, 2, 1);
//        graph.setDirectedEdge(3, t, 2);
//        graph.setDirectedEdge(4, t, 4);
//
//        for (Subgraph sub : net.getAugmentingPaths(graph, s, t)){
//            System.out.println(sub.getEdges());
//        }
//
//    }
}
