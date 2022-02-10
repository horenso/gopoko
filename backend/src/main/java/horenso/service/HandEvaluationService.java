package horenso.service;

import horenso.model.Card;
import horenso.model.Hand;

import java.util.List;

public interface HandEvaluationService {
    /**
     * Finds the best hand out of the seven available cards.
     * If there are multiple equal hands, e.g two equal straights, the five community cards will be preferred
     * over the two hand-cards, also cards with lower suit ids will be preferred over cards with higher suit ids.
     *
     * @param cardList must consist of seven valid cards, the first two cards (index 0 and 1) represent the hand
     * @return Hand object calculated from the provided hand, including the 5 cards that compose the hand
     */
    Hand rateHand(List<Card> cardList);

    /**
     * Given a list a hands, return the strongest hand. In a case of a draw, returns the set of winners.
     *
     * @param hands a list of hands
     * @return the strongest hand, considering first the hand type and then the hand cards
     */
    List<Hand> findWinnerSet(List<Card> hands);
}
