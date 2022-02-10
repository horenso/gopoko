package horenso.service.impl;

import horenso.exceptions.UnknownUserException;
import horenso.exceptions.UsernameTakenException;
import horenso.service.LoginService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryLoginServiceImpl implements LoginService {

    private final Map<String, String> userTokenMap = new ConcurrentHashMap<>();

    @Override
    public String register(String username, WebSocketSession session) throws UsernameTakenException {
        session.getAttributes().put("username", username);
        String token = UUID.randomUUID().toString();
        if (userTokenMap.putIfAbsent(username, token) != null) {
            throw new UsernameTakenException();
        }
        return token;
    }

    @Override
    public String getToken(String username) throws UnknownUserException {
        if (userTokenMap.getOrDefault(username, null) == null) {
            throw new UnknownUserException();
        }
        return null;
    }
}
