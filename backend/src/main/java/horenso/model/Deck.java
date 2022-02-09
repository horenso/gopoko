package horenso.model;

import horenso.exceptions.FaultyDeckException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private LinkedList<Card> deck;

    public Deck() {
        this.deck = new LinkedList<>();
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                deck.push(new Card(value, suit));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Card card : deck) {
            s.append(card.toString()).append("\n");
        }
        return s.toString();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card drawCard() {
        return deck.pop();
    }

    public void putCardsBack(List<Card> cards) throws FaultyDeckException {
        deck.addAll(cards);
        if (deck.size() != 52) {
            throw new FaultyDeckException();
        }
    }
}
