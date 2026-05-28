package org.example.baseball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void createGame() {
        assertThat(game).isNotNull();
    }

    @Test
    void throwIllegalArgumentExceptionInvalidInput() {
        assertIllegalArgument(null);
        assertIllegalArgument("12");
        assertIllegalArgument("1234");
        assertIllegalArgument("12s");
        assertIllegalArgument("121");
    }

    private void assertIllegalArgument(String guessNum) {
        try {
            game.guess(guessNum);
            fail("");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void returnSolvedResultIfMatchedNumber() {
        game.question = "123";
        GuessResult result = game.guess("123");


        assertThat(result).isNotNull();
        assertThat(result.isSolved()).isTrue();
        assertThat(result.getStrikes()).isEqualTo(3);
        assertThat(result.getBalls()).isZero();
    }

    @Test
    void returnSolvedResultIfUnMatchedNumber() {
        game.question = "123";
        GuessResult result = game.guess("456");

        assertThat(result).isNotNull();
        assertThat(result.isSolved()).isFalse();
        assertThat(result.getStrikes()).isZero();
        assertThat(result.getBalls()).isZero();
    }
}