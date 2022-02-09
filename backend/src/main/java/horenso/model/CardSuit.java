package horenso.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CardSuit {
    diamonds(0, '♦', 'D'),
    clubs(1, '♣', 'C'),
    hearts(2, '♥', 'H'),
    spades(3, '♠', 'S');

    private int id;
    private char symbol;
    private char letter;

    public static CardSuit fromChar(char character) {
        return switch (character) {
            case 'D', '♦' -> CardSuit.diamonds;
            case 'C', '♣' -> CardSuit.clubs;
            case 'H', '♥' -> CardSuit.hearts;
            case 'S', '♠' -> CardSuit.spades;
            default -> throw new IllegalArgumentException();
        };
    }
}
