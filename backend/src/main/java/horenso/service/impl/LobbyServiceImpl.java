package horenso.service.impl;

import com.google.gson.Gson;
import horenso.endpoint.response.LobbyUpdateResponse;
import horenso.model.Table;
import horenso.service.LobbyService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

@Service
public class LobbyServiceImpl implements LobbyService {
    private final Gson gson;

    public LobbyServiceImpl(Gson gson) {
        this.gson = gson;
        tableList.add(Table.builder().id(0).name("Table 1").description("bla").playerMax(5).build());
        tableList.add(Table.builder().id(1).name("Table 2").description("bli").playerMax(6).build());
        tableList.add(Table.builder().id(2).name("Table 3").description("blu").playerMax(10).build());
    }

    @Override
    public void addTable(Table table) {
        Optional<Table> tableWithSameName =
                tableList.stream().filter(t -> t.getName() == table.getName()).findFirst();
        if (tableWithSameName.isPresent()) {
            System.out.println(String.format("Table with name %s already existed", table.getName()));
        }
        tableList.add(table);
    }

    @Override
    public void joinTable(int tableId, String userName) {
        synchronized (this) {
            Optional<Table> optionalTable = tableList.stream().filter(t -> t.getId() == tableId).findFirst();
            if (optionalTable.isEmpty()) {

            }
        }
    }

    @Override
    public void addSession(WebSocketSession session) {
        sessionList.add(session);
        LobbyUpdateResponse response = new LobbyUpdateResponse(tableList);
        try {
            session.sendMessage(new TextMessage(gson.toJson(response)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
