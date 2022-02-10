package horenso.endpoint;

import horenso.exceptions.UsernameTakenException;
import horenso.model.communication.Response;
import horenso.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginEndpoint {
    private final WebsocketHelper websocketHelper;
    private final LoginService loginService;

    void login(WebSocketSession session, Map value) throws IOException {
        Object username = value.getOrDefault("username", null);
        if (username == null || !(username instanceof String)) {
            websocketHelper.sendResponse(new Response("login", "error: 'username' missing or not string"), session);
            session.close();
            return;
        }
        String token;
        try {
            token = loginService.register(username.toString(), session);
            websocketHelper.sendResponse(new Response("login", "success", token), session);
        } catch (UsernameTakenException e) {
            websocketHelper.sendResponse(new Response("login", "failed: username is already taken"), session);
        }
    }
}

