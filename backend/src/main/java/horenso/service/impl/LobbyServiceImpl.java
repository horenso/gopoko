package horenso.service.impl;

import horenso.exceptions.InvalidTableIdException;
import horenso.model.Table;
import horenso.persistence.entity.HoldemTable;
import horenso.persistence.repository.HoldemTableRepository;
import horenso.service.LobbyService;
import horenso.service.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LobbyServiceImpl implements LobbyService {
    private final WebsocketService websocketService;
    private final HoldemTableRepository holdemTableRepository;

    @Override
    public List<HoldemTable> getTableList() {
        return holdemTableRepository.findAll();
    }

    @Override
    public void addTable(Table table) {
        Optional<Table> tableWithSameName = TABLE_DTO_LIST.stream().filter(t -> t.getName() == table.getName()).findFirst();
        if (tableWithSameName.isPresent()) {
            System.out.println(String.format("HoldemTable with name %s already existed", table.getName()));
            return;
        }
        websocketService.createRoom(String.format("table_%d", table.getId()));
        TABLE_DTO_LIST.add(table);
    }

    @Override
    public void joinTable(int tableId, String userName) throws InvalidTableIdException {
        synchronized (this) {
            Optional<Table> tableWithThatId = TABLE_DTO_LIST.stream().filter(t -> t.getId() == tableId).findFirst();
            if (tableWithThatId.isEmpty()) {
                throw new InvalidTableIdException();
            }
        }
    }
}
