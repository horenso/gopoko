package horenso.oldendpoint;

import horenso.oldendpoint.dto.CardDto;
import horenso.oldendpoint.mapper.CardMapper;
import horenso.model.Card;
import horenso.model.HandRanking;
import horenso.service.HandRankerService;
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

    @Autowired
    public EvaluationController(HandRankerService handRankerService, CardMapper cardMapper) {
        this.handRankerService = handRankerService;
        this.cardMapper = cardMapper;
    }

    private HandRankerService handRankerService;
    private CardMapper cardMapper;

    @PostMapping(path = "/eval")
    public String rateHand(@RequestBody List<CardDto> cardDtoList) {
        List<Card> cardList = cardMapper.toEntityList(cardDtoList);
        String result;
        HandRanking handRanking = handRankerService.rateHand(cardList);
        if (handRanking == null) {
            result = "No result found!";
        } else {
            result = handRanking.getHandType().toString() + " " + handRanking.getCards().toString();
        }
        log.info("{} => '{}'", cardList, result);
        return result;
    }
}
