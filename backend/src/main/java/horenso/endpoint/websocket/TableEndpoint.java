package horenso.endpoint.websocket;

import horenso.endpoint.websocket.notification.ObservingTableNotification;
import horenso.endpoint.websocket.request.ChatMessageRequest;
import horenso.endpoint.websocket.request.ObservingRequest;
import horenso.endpoint.websocket.request.TakeSeatRequest;
import horenso.exceptions.ErrorResponseException;
import horenso.exceptions.InvalidTableIdException;
import horenso.exceptions.SeatOccupiedException;
import horenso.model.TableInfo;
import horenso.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor
@Component
public class TableEndpoint {
    private final TableService tableService;

    void sendMessage(WebSocketSession session, ChatMessageRequest request) throws ErrorResponseException {
        try {
            tableService.sendChatMessage(request.getId(), getUsernameFromSession(session), request.getMessage());
        } catch (InvalidTableIdException e) {
            throw new ErrorResponseException(request.getDest(), e.getMessage(), false);
        }
    }

    public ObservingTableNotification startObservingTable(WebSocketSession session, ObservingRequest request)
            throws ErrorResponseException {
        try {
            tableService.addUserToTableObservers(request.getId(), getUsernameFromSession(session));
            TableInfo tableInfo = tableService.getTableInfo(request.getId());
            return new ObservingTableNotification(tableInfo);
        } catch (InvalidTableIdException e) {
            throw new ErrorResponseException(request.getDest(), e.getMessage(), false);
        }
    }

    public void stopObservingTable(WebSocketSession session, ObservingRequest request) throws ErrorResponseException {
        try {
            tableService.removeUserFromTableObservers(request.getId(), getUsernameFromSession(session));
        } catch (InvalidTableIdException e) {
            throw new ErrorResponseException(request.getDest(), e.getMessage(), false);
        }
    }

    public void takeSeat(WebSocketSession session, TakeSeatRequest request) throws ErrorResponseException {
        try {
            tableService.seatUserOnTable(request.getId(), request.getSeat(), getUsernameFromSession(session));
        } catch (InvalidTableIdException e) {
            throw new ErrorResponseException(request.getDest(), e.getMessage(), false);
        } catch (SeatOccupiedException e) {
            throw new ErrorResponseException(request.getDest(), e.getMessage(), false);
        }
    }

    private String getUsernameFromSession(WebSocketSession session) {
        return (String) session.getAttributes().get("username");
    }

}
