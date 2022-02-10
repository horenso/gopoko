package horenso.service.impl;

import horenso.model.Card;
import horenso.model.CardValue;
import horenso.model.Hand;

import java.util.ArrayList;
import java.util.List;

class HandEvaluator {
    private final List<Card> givenCards;

    private final Card[][] cardTable = new Card[14][4];
    private final int[] valueHistogram = new int[14];

    private Hand bestHandFound;

    public HandEvaluator(List<Card> givenCards) {
        this.givenCards = givenCards;
    }

    public Hand rateHand() {
        fillCardTable();
        checkFlushAndStraightFlush();
        if (bestHandFound == null) {
            checkStraight();
        } else if (bestHandFound.getHandType().ordinal() >= Hand.Type.STRAIGHT_FLUSH.ordinal()) {
            return bestHandFound;
        }
        analyseSets();
        if (bestHandFound == null) {
            List<Card> cards = new ArrayList<>();
            addKickers(cards);
            bestHandFound = new Hand(Hand.Type.HIGH_CARD, cards);
        }
        return bestHandFound;
    }

    private void fillCardTable() {
        givenCards.forEach(card -> {
            int valueIndex = card.getValue().getNumericValue() - 1;
            cardTable[valueIndex][card.getSuit().getId()] = card;
            valueHistogram[valueIndex]++;
            if (card.getValue() == CardValue.ace) {
                cardTable[0][card.getSuit().getId()] = card;
                valueHistogram[0]++;
            }
        });
    }

    private void removeFromCardTable(Card card) {
        int valueIndex = card.getValue().getNumericValue() - 1;
        cardTable[valueIndex][card.getSuit().getId()] = null;
        if (card.getValue() == CardValue.ace) {
            cardTable[0][card.getSuit().getId()] = null;
        }
    }

    private void checkFlushAndStraightFlush() {
        for (int suitValue = 0; suitValue < 4 && bestHandFound == null; suitValue++) {
            int flushStreak = 0;
            int straightFlushStreak = 0;
            for (int i = cardTable.length - 1; i >= 0; i--) {
                if (cardTable[i][suitValue] != null) {
                    flushStreak++;
                    straightFlushStreak++;
                } else {
                    straightFlushStreak = 0;
                }
                if (straightFlushStreak == 5) {
                    List<Card> cards = new ArrayList<>(5);
                    for (int j = i + 4; j >= i; j--) {
                        cards.add(cardTable[j][suitValue]);
                    }
                    Hand.Type handType;
                    if (cards.get(0).getValue() == CardValue.ace) {
                        handType = Hand.Type.ROYAL_FLUSH;
                    } else {
                        handType = Hand.Type.STRAIGHT_FLUSH;
                    }
                    bestHandFound = new Hand(handType, cards);
                    return;
                }
                if (flushStreak == 5 && i != 0) { // i != 0 because A is in two places
                    List<Card> cards = new ArrayList<>(5);
                    int found = 0;

                    for (int j = cardTable.length - 1; j >= i && found < 5; j--) {
                        Card card = cardTable[j][suitValue];
                        if (card != null) {
                            cards.add(card);
                            found++;
                        }
                    }

                    bestHandFound = new Hand(Hand.Type.FLUSH, cards);
                }
            }
        }
    }

    private void checkStraight() {
        int straightStreak = 0;
        for (int i = cardTable.length - 1; i >= 0; i--) {
            boolean hasValue = false;
            for (int suitValue = 0; suitValue < 4; suitValue++) {
                if (cardTable[i][suitValue] != null) {
                    hasValue = true;
                    break;
                }
            }
            if (hasValue) {
                straightStreak++;
            } else {
                straightStreak = 0;
            }
            if (straightStreak == 5) {
                List<Card> cards = new ArrayList<>(5);
                for (int j = i + 4; j >= i; j--) {
                    Card candidate = null;
                    Card nextCard = null;
                    for (int suitValue = 0; suitValue < 4; suitValue++) {
                        Card card = cardTable[j][suitValue];
                        if (card != null) {
                            // I prefer cards that are not in the player's hand
                            if (givenCards.get(0) != card && givenCards.get(1) != card) {
                                nextCard = card;
                                break;
                            } else if (candidate == null) {
                                candidate = card;
                            }
                        }
                    }
                    if (nextCard == null) {
                        nextCard = candidate;
                    }
                    if (nextCard != null) {
                        cards.add(nextCard);
                        removeFromCardTable(nextCard);
                    }
                }
                bestHandFound = new Hand(Hand.Type.STRAIGHT, cards);
                return;
            }
        }
    }

    private void analyseSets() {
        CardValue bestPair = null;
        CardValue secondPair = null;
        CardValue bestTriple = null;
        CardValue quadruple = null;

        loop:
        for (int i = cardTable.length - 1; i >= 1; i--) {
            switch (valueHistogram[i]) {
                case 4 -> {
                    quadruple = CardValue.fromInt(i + 1);
                    break loop;
                }
                case 3 -> {
                    if (bestTriple == null) {
                        bestTriple = CardValue.fromInt(i + 1);
                    } else if (bestPair == null) {
                        bestPair = CardValue.fromInt(i + 1);
                    }
                }
                case 2 -> {
                    if (bestPair == null) {
                        bestPair = CardValue.fromInt(i + 1);
                    } else if (secondPair == null) {
                        secondPair = CardValue.fromInt(i + 1);
                    }
                }
            }
        }

        if (quadruple != null) {
            List<Card> cards = new ArrayList<>(5);
            addCardsWithValueIndex(cards, quadruple);
            addKickers(cards);
            bestHandFound = new Hand(Hand.Type.FOUR_OF_A_KIND, cards);
        } else if (bestTriple != null) {
            Hand.Type handType;
            List<Card> cards = new ArrayList<>(5);
            if (bestPair != null) {
                handType = Hand.Type.FULL_HOUSE;
                addCardsWithValueIndex(cards, bestTriple);
                addCardsWithValueIndex(cards, bestPair);
            } else if (bestHandFound == null) {
                handType = Hand.Type.THREE_OF_A_KIND;
                addCardsWithValueIndex(cards, bestTriple);
                addKickers(cards);
            } else {
                return;
            }
            bestHandFound = new Hand(handType, cards);
        } else if (bestPair != null && bestHandFound == null) {
            Hand.Type handType;
            List<Card> cards = new ArrayList<>(5);
            if (secondPair == null) {
                handType = Hand.Type.PAIR;
                addCardsWithValueIndex(cards, bestPair);
            } else {
                handType = Hand.Type.TWO_PAIR;
                addCardsWithValueIndex(cards, bestPair);
                addCardsWithValueIndex(cards, secondPair);
            }
            addKickers(cards);
            bestHandFound = new Hand(handType, cards);
        }
    }

    /**
     * Add kickers from the table
     * Table cards are preferred over hand cards (index 0 and 1)
     *
     * @param cards list of cards to add kickers until the list contains exactly five cards
     */
    private void addKickers(List<Card> cards) {
        for (int i = cardTable.length - 1; i >= 0; i--) {
            if (valueHistogram[i] == 0) {
                continue;
            }
            addCardsWithValueIndex(cards, CardValue.fromInt(i + 1));
        }
    }

    private void addCardsWithValueIndex(List<Card> cards, CardValue cardValue) {
        for (int suitValue = 0; suitValue < 4 && cards.size() < 5; suitValue++) {
            addCard(cards, cardValue, suitValue, false);
        }
        for (int suitValue = 0; suitValue < 4 && cards.size() < 5; suitValue++) {
            addCard(cards, cardValue, suitValue, true);
        }
    }

    private void addCard(List<Card> cards, CardValue cardValue, int suitIndex, boolean includeFromHand) {
        Card card = cardTable[cardValue.getNumericValue() - 1][suitIndex];
        if (card == null) {
            return;
        }

        // Check whether the card is included from the hand
        for (int i = 0; i < 2; i++) {
            if (card.equals(givenCards.get(i))) {
                if (includeFromHand) {
                    cards.add(card);
                    removeFromCardTable(card);
                }
                return;
            }
        }
        cards.add(card);
        removeFromCardTable(card);
    }
}
