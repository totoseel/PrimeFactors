package org.example.similaritycheckers;

import java.util.HashSet;
import java.util.Set;

public class AlphaSimilarityChecker {

    private static final int MAX_SCORE = 40;

    public int calculate(String a, String b) {
        Set<Character> setA = toUniqueChars(a);
        Set<Character> setB = toUniqueChars(b);

        Set<Character> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);

        if (intersection.isEmpty()) {
            return 0;
        }
        return MAX_SCORE;
    }

    private Set<Character> toUniqueChars(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        return set;
    }
}
