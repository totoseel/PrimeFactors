package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PrimeFactorsTest {
    PrimeFactors primeFactors = new PrimeFactors();
    @Test
    void testConstantCase() {
        List<Integer> calculate_2 = primeFactors.calculate(2);
        Assertions.assertEquals(2, calculate_2.getFirst());

        List<Integer> calculate_3 = primeFactors.calculate(3);
        Assertions.assertEquals(3, calculate_3.getFirst());

        List<Integer> calculate_4 = primeFactors.calculate(4);
        Assertions.assertEquals(2, calculate_4.getFirst());
        Assertions.assertEquals(2, calculate_4.get(1));
    }

    @Test
    void testInvalidInput() {
        Assertions.assertEquals(0, primeFactors.calculate(-1).size());

        Assertions.assertEquals(0, primeFactors.calculate(0).size());

        Assertions.assertEquals(0, primeFactors.calculate(1).size());
    }
}