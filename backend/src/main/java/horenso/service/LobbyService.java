package horenso.service;

import horenso.model.Table;
import horenso.persistence.entity.HoldemTable;

import java.util.List;

public interface LobbyService {
    public List<HoldemTable> getTableList();

    void addTable(Table table);
}
