package horenso.service;

import horenso.model.Card;
import horenso.model.HandRanking;

import java.util.List;

public interface HandRankerService {
    /**
     * Finds the best hand out of the seven available cards.
     * If there are multiple equal hands, e.g two equal straights, the five community cards will be preferred
     * over the two hand-cards, also cards with lower suit ids will be preferred over cards with higher suit ids.
     *
     * @param cardList must consist of seven valid cards, the first two cards (index 0 and 1) represent the hand
     * @return HandRanking object calculated from the provided hand, including the 5 cards that compose the hand
     */
    HandRanking rateHand(List<Card> cardList);
}
