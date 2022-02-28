package horenso.service.impl;

import horenso.model.Table;
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
    public void addTable(Table table) {
//        Optional<Table> tableWithSameName = TABLE_DTO_LIST.stream().filter(t -> t.getName() == table.getName()).findFirst();
//        if (tableWithSameName.isPresent()) {
//            System.out.println(String.format("HoldemTable with name %s already existed", table.getName()));
//            return;
//        }
//        websocketService.createRoom(String.format("table_%d", table.getId()));
//        TABLE_DTO_LIST.add(table);
    }
}
