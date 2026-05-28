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
}
