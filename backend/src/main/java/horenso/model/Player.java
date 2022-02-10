package horenso.model;

public class Player {
    private final String name;
    private final Card[] hand = new Card[2];
    private int chips;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
    }

    public Card[] getHand() {
        return hand;
    }

    public void clearHand() {
        hand[0] = null;
        hand[1] = null;
    }

    public void receiveHand(Card leftCard, Card rightCard) {
        hand[0] = leftCard;
        hand[1] = rightCard;
    }

    public int getChips() {
        return chips;
    }

    public void receiveChips(int chips) {
        if (chips <= 0) {
            throw new IllegalArgumentException("Chips to add need to be a positive none-zero number!");
        }
        this.chips += chips;
    }

    @Override
    public String toString() {
        return String.format("Player [%-10s Chips: %d", name + "]", chips);
    }
}
