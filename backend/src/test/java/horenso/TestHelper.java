package horenso;

import horenso.model.Card;

import java.util.List;

public class TestHelper {
    public static List<Card> readCardsFromString(String cardString, int number) {
        String[] stringCards = cardString.split(" ");
        Card[] hand = new Card[number];

        for (int i = 0; i < number; ++i) {
            hand[i] = new Card(stringCards[i]);
        }

        return List.of(hand);
    }
}
