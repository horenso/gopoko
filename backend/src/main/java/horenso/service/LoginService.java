package horenso.service;

import horenso.exceptions.UnknownUserException;
import horenso.exceptions.UsernameTakenException;
import org.springframework.web.socket.WebSocketSession;

public interface LoginService {


    String register(String username, WebSocketSession session) throws UsernameTakenException;

    String getToken(String username) throws UnknownUserException;
}
