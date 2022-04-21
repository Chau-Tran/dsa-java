
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
package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.*;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {

        String pre = prefix.trim();
        List<String> candidates = new ArrayList<>();
        TrieNode<List<String>> node = getRoot();
        StringBuffer current = new StringBuffer();

        if(find(pre) == null) { // prefix doesnt exist
            put(pre, null);
            find(pre).setEndState(false);
            return candidates;
        }
        else { // prefix exists
            if (find(pre).getValue() == null) { // prefix doesnt have cand list

                for (char letter : pre.toCharArray()) {
                    node = node.getChild(letter);
                    if (node == null) {
                        return candidates;
                    }
                    current.append(letter);
                }

                generateCand(node, candidates, current);

                candidates.sort((o1, o2) -> {
                    if (o1.length() != o2.length()) return o1.length() - o2.length();
                    return o1.compareTo(o2);
                });
                find(pre).setValue(candidates);
            }
            else{ // prefix has cand list
                candidates = find(pre).getValue();
            }
        }

        if(candidates.size() > getMax()){
            candidates = candidates.subList(0, getMax());}

        return candidates;
    }

    public void generateCand(TrieNode<List<String>> node, List<String> candidates, StringBuffer current) {
        if (node.isEndState()) {candidates.add(current.toString());}
        if (!node.hasChildren()) return;
        for (TrieNode child : node.getChildrenMap().values()) {
            generateCand(child, candidates, current.append(child.getKey()));
            current.setLength(current.length() - 1);
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {

        String pre = prefix.trim();
        List<String> prefixList;

        if(get(candidate) == null){ // candidate is a prefix, not a word
            if(find(candidate) != null){
                put(candidate, find(candidate).getValue());
            }
            else{
                put(candidate, null);
            }
        }

        if(find(pre) != null) { // prefix found
            if (find(pre).getValue() == null) prefixList = getCandidates(pre); // prefix doesnt have cand list
            else prefixList = find(pre).getValue(); // prefix has cand list

            if (!prefixList.contains(candidate)) { // cand list of prefix doesnt have cand
                put(candidate, null);
            }

            prefixList.remove(candidate);
            prefixList.add(0, candidate);
            find(pre).setValue(prefixList);
        }
        else { // prefix does not exist
            getCandidates(pre);
            pickCandidate(pre, candidate);
        }
    }

    public static void main(String[] args) {
        final String dict_file = "src/main/resources/dict.txt";
        final int max = 5;

        Autocomplete<?> ac = new AutocompleteHW(dict_file, max);

//        System.out.println(ac.getCandidates("nonsubs"));
//        ac.pickCandidate("nonsubs", "nonsubsidi");
////        System.out.println(ac.getCandidates("nonsubs"));
//
//        ac.pickCandidate("nonsubsid", "nonsubsidy");
////        System.out.println(ac.getCandidates("nonsubsid"));
//
//        ac.pickCandidate("nonsubs", "nonsubsidz");
//        System.out.println(ac.getCandidates("nonsubsid"));

//        System.out.println(ac.getCandidates("jinho"));
        System.out.println(ac.getCandidates("_"));
        ac.pickCandidate("_", "_jinho");
        System.out.println(ac.getCandidates("_"));
//        System.out.println(ac.getCandidates("jinho"));


    }

}
