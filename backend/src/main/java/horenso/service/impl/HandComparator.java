package horenso.service.impl;

import horenso.model.Hand;

import java.util.Comparator;

public class HandComparator implements Comparator<Hand> {

    @Override
    public int compare(Hand hand1, Hand hand2) {
        // first compare the hand types
        int type1 = hand1.getHandType().ordinal();
        int type2 = hand2.getHandType().ordinal();
        if (type1 < type2) {
            return -1;
        }
        if (type1 > type2) {
            return 1;
        }
        // then compare each card individually
        for (int i = 0; i < 5; i++) {
            int value1 = hand1.getCards().get(i).getValue().ordinal();
            int value2 = hand2.getCards().get(i).getValue().ordinal();
            if (value1 < value2) {
                return -1;
            }
            if (value1 > value2) {
                return 1;
            }
        }
        return 0;
    }
}
