package horenso.endpoint.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebsocketSessionManager {
    private Map<String, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

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
}
