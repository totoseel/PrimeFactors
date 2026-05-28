package org.example.similaritycheckers;

public class LengthSimilarityChecker {

    private static final int MAX_SCORE = 60;

    public int calculate(String a, String b) {
        int longer = longerLength(a, b);
        int shorter = shorterLength(a, b);
        if (isDoubleLengthOrMore(longer, shorter)) {
            return 0;
        }
        return MAX_SCORE;
    }

    private int longerLength(String a, String b) {
        return Math.max(a.length(), b.length());
    }

    private int shorterLength(String a, String b) {
        return Math.min(a.length(), b.length());
    }

    private boolean isDoubleLengthOrMore(int longer, int shorter) {
        return longer >= shorter * 2;
    }
}
