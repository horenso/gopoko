package horenso.service;

import horenso.endpoint.websocket.notification.Notification;
import org.springframework.web.socket.WebSocketSession;

public interface WebsocketManagerService {
    void sendToOneUser(String username, Notification notification);

    void broadcast(String room, Notification notification);

    void createRoom(String room);

    void subscribe(WebSocketSession session, String room);

    void unsubscribe(WebSocketSession session, String room);
}
