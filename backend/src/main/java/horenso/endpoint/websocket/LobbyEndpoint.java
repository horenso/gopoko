package horenso.endpoint.websocket;

import horenso.endpoint.websocket.request.JoinRequest;
import horenso.endpoint.websocket.response.JoinResponse;
import horenso.endpoint.websocket.response.TableListResponse;
import horenso.exceptions.ErrorResponseException;
import horenso.exceptions.FullTableException;
import horenso.exceptions.InvalidTableIdException;
import horenso.model.Table;
import horenso.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LobbyEndpoint {
    private final LobbyService lobbyService;

    public TableListResponse getTableList() {
        List<Table> tableList = lobbyService
                .getTableList()
                .stream()
                .map(t -> new Table(t.getId(), t.getName()))
                .collect(Collectors.toList());
        return new TableListResponse(tableList);
    }

    public JoinResponse joinTable(WebSocketSession session, JoinRequest request) throws ErrorResponseException {
        try {
            if (!session.getAttributes().containsKey("username")) {
                throw new ErrorResponseException("join", "session must contain a username header", true);
            }
            lobbyService.joinTable(request.getId(), (String) session.getAttributes().get("username"));
            return new JoinResponse();
        } catch (InvalidTableIdException e) {
            throw new ErrorResponseException(
                    "join", String.format("table with id %d doesn't exist", request.getId()), false);
        } catch (FullTableException e) {
            throw new ErrorResponseException("join", "table is already full", false);
        }
    }
}
