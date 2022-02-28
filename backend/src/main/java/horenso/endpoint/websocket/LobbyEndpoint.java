package horenso.endpoint.websocket;

import horenso.endpoint.websocket.notification.TableListNotification;
import horenso.model.Table;
import horenso.persistence.repository.ObservingUserRepository;
import horenso.service.LobbyService;
import horenso.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class LobbyEndpoint {
    private final LobbyService lobbyService;
    private final TableService tableService;
    private final ObservingUserRepository observingUserRepository;

    public TableListNotification getTableList() {
        List<Table> tableList = lobbyService
                .getTableList()
                .stream()
                .map(t -> new Table(t.getId(), t.getName()))
                .collect(Collectors.toList());
        return new TableListNotification(tableList);
    }

    public void createTable() {
        lobbyService.createTable();
    }
}
