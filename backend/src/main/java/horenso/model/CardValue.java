package horenso.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CardValue {
    two(2, '2'),
    three(3, '3'),
    four(4, '4'),
    five(5, '5'),
    six(6, '6'),
    seven(7, '7'),
    eight(8, '8'),
    nine(9, '9'),
    ten(10, 'T'),
    jack(11, 'J'),
    queen(12, 'Q'),
    king(13, 'K'),
    ace(14, 'A');

    private int numericValue;
    private char character;

    public static CardValue fromChar(char text) {
        return switch (text) {
            case 'A' -> CardValue.ace;
            case 'K' -> CardValue.king;
            case 'Q' -> CardValue.queen;
            case 'J' -> CardValue.jack;
            case 'T' -> CardValue.ten;
            default -> {
                try {
                    yield CardValue.values()[Character.getNumericValue(text) - 2];
                } catch (IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException();
                }
            }
        };
    }

    public static CardValue fromInt(int value) {
        if (value < 1 || value > 14) {
            throw new IllegalArgumentException();
        }
        if (value == 1) {
            return CardValue.ace;
        }
        return CardValue.values()[value - 2];
    }
}
