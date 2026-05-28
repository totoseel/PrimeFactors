package org.example;

import org.example.primefactors.PrimeFactors;
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


        List<Integer> calculate_15 = primeFactors.calculate(15);
        Assertions.assertEquals(3, calculate_15.getFirst());
        Assertions.assertEquals(5, calculate_15.get(1));

        List<Integer> calculate_17 = primeFactors.calculate(17);
        Assertions.assertEquals(17, calculate_17.getFirst());

        List<Integer> calculate_23 = primeFactors.calculate(23);
        Assertions.assertEquals(23, calculate_23.getFirst());


        List<Integer> calculate_126 = primeFactors.calculate(126);
        Assertions.assertEquals(2, calculate_126.getFirst());
        Assertions.assertEquals(3, calculate_126.get(1));
        Assertions.assertEquals(3, calculate_126.get(2));
        Assertions.assertEquals(7, calculate_126.get(3));
    }

    @Test
    void testInvalidInput() {
        Assertions.assertEquals(0, primeFactors.calculate(-1).size());
        Assertions.assertEquals(0, primeFactors.calculate(0).size());
        Assertions.assertEquals(0, primeFactors.calculate(1).size());
    }
}