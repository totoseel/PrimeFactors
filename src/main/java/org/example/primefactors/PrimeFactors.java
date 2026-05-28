package org.example.primefactors;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactors {
    public List<Integer> calculate(int number){
        List<Integer> factors = new ArrayList<>();
        if (isInvalidInput(number)) {
            return factors;
        }

        findPrimeFactor(number, factors);

        return factors;
    }

    private boolean isInvalidInput(int number) {
        return number < 2;
    }

    private void findPrimeFactor(int number, List<Integer> factors) {
        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                factors.add(i);
                number /= i;
            }
        }

        if (number > 1) {
            factors.add(number);
        }
    }
}
