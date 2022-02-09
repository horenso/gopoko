package horenso.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
public class HandRanking {
    private HandType handType;
    private List<Card> cards;
}
