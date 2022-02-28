package horenso.endpoint.websocket.notification;

import java.time.LocalDateTime;

public class ChatMessageNotification extends Notification {
    private final String sender;
    private final String message;
    private final LocalDateTime time;

    public ChatMessageNotification(String sender, String message, LocalDateTime time) {
        super("chat_message", true);
        this.sender = sender;
        this.message = message;
        this.time = time;
    }
}
