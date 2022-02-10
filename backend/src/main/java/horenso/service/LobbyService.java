package horenso.service;

import horenso.exceptions.FullTableException;
import horenso.exceptions.InvalidTableIdException;
import horenso.model.Table;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public interface LobbyService {
    public List<Table> tableList = new ArrayList<>();
    public List<WebSocketSession> sessionList = new ArrayList<>();

    void addTable(Table table);

    void joinTable(int tableId, String userName) throws InvalidTableIdException, FullTableException;

    void addSession(WebSocketSession session);
}
