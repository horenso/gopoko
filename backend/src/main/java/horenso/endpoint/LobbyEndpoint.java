package horenso.endpoint;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import horenso.exceptions.UsernameTakenException;
import horenso.model.communication.Response;
import horenso.service.LobbyService;
import horenso.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LobbyEndpoint extends TextWebSocketHandler {
    private final LobbyService lobbyService;
    private final LoginService loginService;
    private final Gson gson;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        System.out.println("in handleTextMessage()");
        Map value;
        try {
            value = gson.fromJson(message.getPayload(), Map.class);
        } catch (JsonSyntaxException e) {
            sendResponse(new Response("/", "error: invalid JSON"), session);
            session.close();
            return;
        }
        System.out.println("Got valid JSON:" + value.toString());
        String command = value.getOrDefault("cmd", null).toString();
        if (command == null) {
            sendResponse(new Response("/", "error: field 'cmd' missing"), session);
            session.close();
            return;
        }
        switch(command) {
            case "login" -> login(session, value);
            default -> {
                sendResponse(new Response("/", String.format("error: cmd %s invalid", command)), session);
                session.close();
            }
        }
    }

    void login(WebSocketSession session, Map value) throws IOException {
        Object username = value.getOrDefault("username", null);
        if (username == null || !(username instanceof String)) {
            sendResponse(new Response("login", "error: 'username' missing or not string"), session);
            session.close();
            return;
        }
        String token;
        try {
            token = loginService.register(username.toString());
            sendResponse(new Response("login", "success", token), session);
        } catch (UsernameTakenException e) {
            sendResponse(new Response("login", "failed: username is already taken"), session);
        }
    }

    private void sendResponse(Response response, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(gson.toJson(response)));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection has been established");
        lobbyService.addSession(session);
    }
}
