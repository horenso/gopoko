package horenso.endpoint.websocket.request;

import lombok.Data;

@Data
public class ChatMessageRequest extends Request {
    private final long id;
    private final String message;

    public ChatMessageRequest(long id, String message) {
        super("chat_message");
        this.id = id;
        this.message = message;
    }
}
