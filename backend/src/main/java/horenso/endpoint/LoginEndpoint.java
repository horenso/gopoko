package horenso.endpoint;

import horenso.endpoint.request.LoginRequest;
import horenso.endpoint.response.LoginResponse;
import horenso.exceptions.ErrorResponseException;
import horenso.exceptions.UsernameTakenException;
import horenso.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@RequiredArgsConstructor
public class LoginEndpoint {
    private final LoginService loginService;

    public LoginResponse login(WebSocketSession session, LoginRequest loginRequest) throws ErrorResponseException {
        // TODO: Would be nice to one universal way to serialize and deserialize objects and
        // check required and not required fields
        if (loginRequest.getUsername() == null) {
            throw new ErrorResponseException(loginRequest.getDest(), "\"username\" missing or not string", true);
        }
        String token;
        try {
            token = loginService.register(loginRequest.getUsername(), session);
            return new LoginResponse(token);
        } catch (UsernameTakenException e) {
            throw new ErrorResponseException(loginRequest.getDest(), "username is already taken", false);
        }
    }
}

