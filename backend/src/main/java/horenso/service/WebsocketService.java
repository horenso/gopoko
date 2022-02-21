package horenso.service;

import horenso.endpoint.websocket.response.Response;
import org.springframework.web.socket.WebSocketSession;

public interface WebsocketService {
    void sendToOneUser(String username, Response response);

    void broadcast(String room, Response response);

    void createRoom(String room);

    void subscribe(WebSocketSession session, String room);

    void unsubscribe(WebSocketSession session, String room);
}
