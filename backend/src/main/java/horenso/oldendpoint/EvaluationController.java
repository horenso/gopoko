package horenso.oldendpoint;

import horenso.model.Card;
import horenso.model.Hand;
import horenso.oldendpoint.dto.CardDto;
import horenso.oldendpoint.mapper.CardMapper;
import horenso.service.HandEvaluationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@CrossOrigin
public class EvaluationController {

    private HandEvaluationService handEvaluationService;

    @Autowired
    public EvaluationController(HandEvaluationService handEvaluationService, CardMapper cardMapper) {
        this.handEvaluationService = handEvaluationService;
        this.cardMapper = cardMapper;
    }

    private CardMapper cardMapper;

    @PostMapping(path = "/eval")
    public String rateHand(@RequestBody List<CardDto> cardDtoList) {
        List<Card> cardList = cardMapper.toEntityList(cardDtoList);
        String result;
        Hand hand = handEvaluationService.rateHand(cardList);
        if (hand == null) {
            result = "No result found!";
        } else {
            result = hand.getHandType().toString() + " " + hand.getCards().toString();
        }
        log.info("{} => '{}'", cardList, result);
        return result;
    }
}
