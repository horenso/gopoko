package horenso.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public final class Card {
    private final CardValue value;
    private final CardSuit suit;

    public Card(String cardString) {
        if (cardString.length() != 2) {
            throw new IllegalArgumentException();
        }
        this.value = CardValue.fromChar(cardString.charAt(0));
        this.suit = CardSuit.fromChar(cardString.charAt(1));
    }

    public String toWord() {
        return suit.getSymbol() + " " + value.name();
    }

    @Override
    public String toString() {
        return value.getCharacter() + "" + suit.getSymbol();
    }
}
