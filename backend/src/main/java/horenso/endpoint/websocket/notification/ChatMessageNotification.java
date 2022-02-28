package horenso.endpoint.websocket.notification;

import horenso.model.ChatMessage;

public class ChatMessageNotification extends Notification {
    private ChatMessage chatMessage;

    public ChatMessageNotification(ChatMessage chatMessage) {
        super("chat_message", true);
        this.chatMessage = chatMessage;
    }
}
