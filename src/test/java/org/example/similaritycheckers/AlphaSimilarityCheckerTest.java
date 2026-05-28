package org.example.similaritycheckers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlphaSimilarityCheckerTest {

    private AlphaSimilarityChecker checker;

    @BeforeEach
    void setUp() {
        checker = new AlphaSimilarityChecker();
    }

    @Test
    void identicalAlphabetSet_returns40() {
        assertThat(checker.calculate("ASD", "DSA")).isEqualTo(40);
    }

    @Test
    void noCommonAlphabets_returns0() {
        assertThat(checker.calculate("A", "BB")).isEqualTo(0);
    }

    @Test
    void partialCommonAlphabets_returnsPartialScore() {
        // setA={A}, setB={A,E}, union=2, intersection=1 → 1/2 * 40 = 20
        assertThat(checker.calculate("AA", "AAE")).isEqualTo(20);
    }

    @Test
    void repeatedCharsWithSameUniqueSet_returns40() {
        // setA={A,B}, setB={B,A}, union=2, intersection=2 → 2/2 * 40 = 40
        assertThat(checker.calculate("AAABB", "BA")).isEqualTo(40);
    }
}
