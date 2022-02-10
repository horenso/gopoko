package horenso.service.impl;

import horenso.model.Card;
import horenso.model.Hand;
import horenso.service.HandEvaluationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandEvaluationServiceImpl implements HandEvaluationService {
    @Override
    public Hand rateHand(List<Card> cardList) {
        HandEvaluator handEvaluator = new HandEvaluator(cardList);
        return handEvaluator.rateHand();
    }

    @Override
    public List<Hand> findWinnerSet(List<Card> hands) {
        return null;
    }
}
