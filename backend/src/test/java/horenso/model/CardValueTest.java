package horenso.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardValueTest {

    @Test
    void fromChar() {
        assertEquals(CardValue.ace, CardValue.fromChar('A'));
        assertEquals(CardValue.king, CardValue.fromChar('K'));
        assertEquals(CardValue.queen, CardValue.fromChar('Q'));
        assertEquals(CardValue.jack, CardValue.fromChar('J'));
        assertEquals(CardValue.ten, CardValue.fromChar('T'));
        assertEquals(CardValue.two, CardValue.fromChar('2'));
        assertEquals(CardValue.seven, CardValue.fromChar('7'));

        assertThrows(IllegalArgumentException.class, () -> CardValue.fromChar('1'));
        assertThrows(IllegalArgumentException.class, () -> CardValue.fromChar('Y'));
        assertThrows(IllegalArgumentException.class, () -> CardValue.fromChar(' '));
    }
}
