package horenso.service.impl;

import horenso.model.Card;
import horenso.model.HandRanking;
import horenso.service.HandRankerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandRankerServiceImpl implements HandRankerService {
    @Override
    public HandRanking rateHand(List<Card> cardList) {
        HandRanker handRanker = new HandRanker(cardList);
        return handRanker.rateHand();
    }
}
