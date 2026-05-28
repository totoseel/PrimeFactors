package org.example.similaritycheckers;

import java.util.HashSet;
import java.util.Set;

public class AlphaSimilarityChecker {

    private static final int MAX_SCORE = 40;

    public int calculate(String a, String b) {
        Set<Character> setA = toUniqueChars(a);
        Set<Character> setB = toUniqueChars(b);

        int sameCnt = sameCnt(setA, setB);
        int totalCnt = totalCnt(setA, setB);

        return (int) ((double) sameCnt / totalCnt * MAX_SCORE);
    }

    private int sameCnt(Set<Character> setA, Set<Character> setB) {
        return intersection(setA, setB).size();
    }

    private int totalCnt(Set<Character> setA, Set<Character> setB) {
        return union(setA, setB).size();
    }

    private Set<Character> toUniqueChars(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        return set;
    }

    private Set<Character> intersection(Set<Character> setA, Set<Character> setB) {
        Set<Character> result = new HashSet<>(setA);
        result.retainAll(setB);
        return result;
    }

    private Set<Character> union(Set<Character> setA, Set<Character> setB) {
        Set<Character> result = new HashSet<>(setA);
        result.addAll(setB);
        return result;
    }
}
