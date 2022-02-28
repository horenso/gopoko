package horenso.endpoint.websocket;

import com.google.gson.Gson;
import horenso.endpoint.websocket.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class handles all open Websocket sessions. It is the only state-management in the backend, that is not
 * database driven. The WebsocketSessionManager allows sending Notifications to both a specific session and
 * to all sessions from a given user.
 */

@RequiredArgsConstructor
@Component
public class WebsocketSessionManager {
    private final Gson gson;
    private final Map<String, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession session) {
        String username = (String) session.getAttributes().getOrDefault("username", null);
        synchronized (this) {
            userSessions.putIfAbsent(username, new LinkedList<>());
            userSessions.get(username).add(session);
        }
        System.out.println(userSessions.toString());
    }

    public void removeSession(WebSocketSession session) {
        String username = (String) session.getAttributes().getOrDefault("username", null);
        synchronized (this) {
            List<WebSocketSession> sessionList = userSessions.get(username);
            sessionList.remove(session);
            if (sessionList.isEmpty()) {
                userSessions.remove(username);
            }
        }
        System.out.println(userSessions.toString());
    }

    public void sendToOneSession(WebSocketSession session, Notification notification) throws IOException {
        String message = gson.toJson(notification);
        session.sendMessage(new TextMessage(message));
    }

    public void sendToUser(String username, Notification notification) {
        List<WebSocketSession> sessionList = userSessions.get(username);
        if (sessionList != null) {
            sessionList.forEach(session -> {
                try {
                    sendToOneSession(session, notification);
                } catch (IOException e) {
                    e.printStackTrace(); // TODO: What to do with an IOException?
                }
            });
        }
    }
}
