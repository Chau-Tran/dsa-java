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
package edu.emory.cs.dynamic.lcs;


import java.util.HashSet;
import java.util.Set;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class LCSQuiz extends LCSDynamic {
    /**
     * @param a the first string.
     * @param b the second string.
     * @return a set of all longest common sequences between the two strings.
     */
    static int arr[][];

    static void table(String s1, String s2) {

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    arr[i][j] = arr[i - 1][j - 1] + 1;
                else
                    arr[i][j] = Math.max(arr[i - 1][j], arr[i][j - 1]);
            }
        }
    }

    static Set<String> solveAll(String s1, String s2){
        arr = new int[s1.length() + 1][s2.length() + 1];
        return aux(s1, s2);
    }

    static Set<String> aux(String s1, String s2) {
        s1 = s1.trim();
        s2 = s2.trim();

        int len1 = s1.length();
        int len2 = s2.length();

        table(s1, s2);

        if (len1 == 0 || len2 == 0) {
            Set<String> set = new HashSet<String>();
            set.add("");
            return set;
        }
        if (s1.charAt(len1 - 1) == s2.charAt(len2 - 1)) {
            Set<String> set = aux(s1.substring(0,s1.length()-1), s2.substring(0,s2.length()-1));
            Set<String> set1 = new HashSet<>();
            for (String temp : set) {
                temp = temp + s1.charAt(len1 - 1);
                set1.add(temp);
            }
            return set1;
        } else {
            Set<String> set = new HashSet<>();
            Set<String> set1 = new HashSet<>();
            if (arr[len1 - 1][len2] >= arr[len1][len2 - 1]) {
                set = aux(s1.substring(0,s1.length()-1), s2);
            }
            if (arr[len1][len2 - 1] >= arr[len1 - 1][len2]) {
                set1 = aux(s1, s2.substring(0,s2.length()-1));
            }
            for (String temp : set) {
                set1.add(temp);
            }
            return set1;
        }
    }

//    public static void main(String[] args) {
////        String s1 = "AGTGATG";
////        String s2 = "GTTAG";
////        arr = new int[s1.length() + 1][s2.length() + 1];
//        System.out.println(solveAll("AGTGATG", "GTTAG"));
//    }
}

