package horenso.service;

import horenso.model.Table;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public interface LobbyService {
    public List<Table> tableList = new ArrayList<>();
    public List<WebSocketSession> sessionList = new ArrayList<>();

    void addTable(Table table);
    void joinTable(String tableName, String userName);
    void addSession(WebSocketSession session);
}
