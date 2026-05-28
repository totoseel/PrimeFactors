package org.example.similaritycheckers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LengthSimilarityCheckerTest {

    private LengthSimilarityChecker checker;

    @BeforeEach
    void setUp() {
        checker = new LengthSimilarityChecker();
    }

    @Test
    void sameLength_returns60() {
        assertThat(checker.calculate("ASD", "DSA")).isEqualTo(60);
    }

    @Test
    void doubleLengthDifference_returns0() {
        assertThat(checker.calculate("A", "BB")).isEqualTo(0);
    }

    @Test
    void partialLengthDifference_returnsPartialScore() {
        // longer=5, shorter=3, gap=2 → (1 - 2/3) * 60 = 20
        assertThat(checker.calculate("AAABB", "BAA")).isEqualTo(20);
    }

    @Test
    void oneCharDifference_returnsPartialScore() {
        // longer=3, shorter=2, gap=1 → (1 - 1/2) * 60 = 30
        assertThat(checker.calculate("AA", "AAE")).isEqualTo(30);
    }
}
