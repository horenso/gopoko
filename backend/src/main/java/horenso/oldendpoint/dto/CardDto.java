package horenso.oldendpoint.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class CardDto {
    public enum Suit {D, C, H, S}

    @NotNull(message = "Suit must not be null")
    private Suit suit;

    @NotNull(message = "Value must not be null")
    @Size(min=1, max=13, message = "Value must be between 1 and 13")
    private Integer value;
}
