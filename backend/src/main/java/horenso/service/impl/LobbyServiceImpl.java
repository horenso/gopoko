package horenso.service.impl;

import com.google.gson.Gson;
import horenso.endpoint.response.LobbyUpdateResponse;
import horenso.exceptions.InvalidTableIdException;
import horenso.model.Table;
import horenso.service.LobbyService;
import horenso.service.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Optional;

@AllArgsConstructor
@Service
public class LobbyServiceImpl implements LobbyService {
    private final Gson gson;
    private final WebsocketService websocketService;

    @Override
    public void subscribeToTableUpdates(WebSocketSession session) {
        websocketService.subscribe(session, "table_list_updates");
        websocketService.sendToOneSession(session, new LobbyUpdateResponse(tableList));
    }

    @Override
    public void addTable(Table table) {
        Optional<Table> tableWithSameName = tableList.stream().filter(t -> t.getName() == table.getName()).findFirst();
        if (tableWithSameName.isPresent()) {
            System.out.println(String.format("HoldemTable with name %s already existed", table.getName()));
            return;
        }
        websocketService.createRoom(String.format("table_%d", table.getId()));
        tableList.add(table);
    }

    @Override
    public void joinTable(int tableId, String userName) throws InvalidTableIdException {
        synchronized (this) {
            Optional<Table> tableWithThatId = tableList.stream().filter(t -> t.getId() == tableId).findFirst();
            if (tableWithThatId.isEmpty()) {
                throw new InvalidTableIdException();
            }
        }
    }
}
