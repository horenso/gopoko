package horenso.service.impl;

import horenso.persistence.entity.HoldemTable;
import horenso.persistence.repository.HoldemTableRepository;
import horenso.service.LobbyService;
import horenso.service.WebsocketManagerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LobbyServiceImpl implements LobbyService {
    private final WebsocketManagerService websocketService;
    private final HoldemTableRepository holdemTableRepository;

    @Override
    public List<HoldemTable> getTableList() {
        return holdemTableRepository.findAll();
    }

    @Override
    public void createTable() {
        HoldemTable table = new HoldemTable();
        table.setName("Test");
        table.setNumberOfUsers(6);
        holdemTableRepository.save(table);
    }
}
