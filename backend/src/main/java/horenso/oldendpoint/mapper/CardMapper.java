package horenso.oldendpoint.mapper;

import horenso.oldendpoint.dto.CardDto;
import horenso.model.Card;
import horenso.model.CardSuit;
import horenso.model.CardValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardMapper {

    public Card toEntity(CardDto cardDto) {
        CardSuit suit;
        CardValue value;

        suit = switch (cardDto.getSuit()) {
            case D -> CardSuit.diamonds;
            case C -> CardSuit.clubs;
            case H -> CardSuit.hearts;
            default -> CardSuit.spades;
        };

        if (cardDto.getValue() == 1) {
            value = CardValue.ace;
        } else {
            value = CardValue.fromInt(cardDto.getValue());
        }

        return Card.builder().suit(suit).value(value).build();
    }

    public CardDto toDto(Card card) {
        int value;

        CardDto.Suit suit = switch (card.getSuit()) {
            case diamonds -> CardDto.Suit.D;
            case clubs -> CardDto.Suit.C;
            case hearts -> CardDto.Suit.H;
            default -> CardDto.Suit.S;
        };

        int numericValue = card.getValue().getNumericValue();

        if (numericValue == 14) {
            value = 1;
        } else {
            value = numericValue;
        }

        return CardDto.builder().suit(suit).value(value).build();
    }

    public List<Card> toEntityList(List<CardDto> cardDtoList) {
        List<Card> cardList = new ArrayList<>();
        cardDtoList.forEach(cardDto -> cardList.add(toEntity(cardDto)));
        return cardList;
    }

    public List<CardDto> toDtoList(List<Card> cardList) {
        List<CardDto> dtoList = new ArrayList<>();
        cardList.forEach(card -> dtoList.add(toDto(card)));
        return dtoList;
    }
}
