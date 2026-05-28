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
}
