package horenso.endpoint;

import horenso.endpoint.request.JoinRequest;
import horenso.endpoint.response.JoinResponse;
import horenso.exceptions.ErrorResponseException;
import horenso.exceptions.FullTableException;
import horenso.exceptions.InvalidTableIdException;
import horenso.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@RequiredArgsConstructor
public class LobbyEndpoint {
    private final LobbyService lobbyService;

    public JoinResponse joinTable(WebSocketSession session, JoinRequest joinRequest) throws ErrorResponseException {
        try {
            if (!session.getAttributes().containsKey("username")) {
                throw new ErrorResponseException("join", "session must contain a username header", true);
            }
            lobbyService.joinTable(joinRequest.getId(), (String) session.getAttributes().get("username"));
        } catch (InvalidTableIdException e) {
            throw new ErrorResponseException(
                    "join", String.format("table with id %d doesn't exist", joinRequest.getId()), false);
        } catch (FullTableException e) {
            throw new ErrorResponseException("join", "table is already full", false);
        }
        return new JoinResponse();
    }
}
