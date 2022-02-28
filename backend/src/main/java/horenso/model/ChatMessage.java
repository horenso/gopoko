package horenso.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private final String username;
    private final String message;
    private final LocalDateTime time;
}
