package horenso.service.impl;

import com.google.gson.Gson;
import horenso.endpoint.websocket.response.Response;
import horenso.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.UncheckedIOException;

@Service
@RequiredArgsConstructor
public class WebsocketServiceImpl implements WebsocketService {
    private final Gson gson;

    @Override
    public void sendToOneSession(WebSocketSession session, Response response) {
        try {
            String message = gson.toJson(response);
            session.sendMessage(new TextMessage(message));
            System.out.println(">> " + message);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void broadcast(String room, Response response) {
//        List<WebSocketSession> sessionList = subscriptions.getOrDefault(room, null);
//        if (sessionList == null) {
//            System.out.println(String.format("tried to broadcast to none existent: %s", room));
//        }
//        sessionList.forEach(session -> sendToOneSession(session, response));
    }

    @Override
    public void createRoom(String room) {
//        List<WebSocketSession> result = subscriptions.putIfAbsent(room, new LinkedList<>());
//        if (result != null) {
//            System.out.println(String.format("room already existed: %s", room));
//        } else {
//            System.out.println(String.format("created room: %s", room));
//        }
    }

    @Override
    public void subscribe(WebSocketSession session, String room) {
//        List<WebSocketSession> sessions = subscriptions.get(room);
//        if (sessions == null) {
//            System.out.println(String.format("tried to subscribe to none existent: %s", room));
//        } else {
//            sessions.add(session);
//        }
    }

    @Override
    public void unsubscribe(WebSocketSession session, String room) {
//        List<WebSocketSession> sessions = subscriptions.get(room);
//        if (sessions == null) {
//            System.out.println(String.format("tried to unsubscribe to none existent: %s", room));
//        }
//        sessions.remove(session);
    }
}
