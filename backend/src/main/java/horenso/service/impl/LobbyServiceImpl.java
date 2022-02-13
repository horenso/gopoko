package horenso.service.impl;

import com.google.gson.Gson;
import horenso.endpoint.response.LobbyUpdateResponse;
import horenso.exceptions.InvalidTableIdException;
import horenso.model.Table;
import horenso.service.LobbyService;
import horenso.service.WebsocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Optional;

@Service
public class LobbyServiceImpl implements LobbyService {
    private final Gson gson;
    private final WebsocketService websocketService;

    public LobbyServiceImpl(Gson gson, WebsocketService websocketService) {
        this.gson = gson;
        this.websocketService = websocketService;
        addTable(Table.builder().id(0).name("Table 1").description("bla").playerMax(5).build());
        addTable(Table.builder().id(1).name("Table 2").description("bli").playerMax(6).build());
        addTable(Table.builder().id(2).name("Table 3").description("blu").playerMax(10).build());
        websocketService.createRoom("lobby");
    }

    @Override
    public void subscribeToTableUpdates(WebSocketSession session) {
        websocketService.subscribe(session, "lobby");
        websocketService.sendToOneSession(session, new LobbyUpdateResponse(tableList));
    }

    @Override
    public void addTable(Table table) {
        Optional<Table> tableWithSameName = tableList.stream().filter(t -> t.getName() == table.getName()).findFirst();
        if (tableWithSameName.isPresent()) {
            System.out.println(String.format("Table with name %s already existed", table.getName()));
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
