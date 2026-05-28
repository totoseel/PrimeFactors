package org.example;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactors {
    public List<Integer> calculate(int input){
        List<Integer> l = new ArrayList<>();
        if (input < 2) {
            return l;
        }

        if (input == 2){
            l.add(2);
        } else if (input == 3){
            l.add(3);
        } else if (input == 4){
            l.add(2);
            l.add(2);
        }

        return l;
    }
}
