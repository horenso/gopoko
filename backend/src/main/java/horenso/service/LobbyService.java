package horenso.service;

import horenso.exceptions.FullTableException;
import horenso.exceptions.InvalidTableIdException;
import horenso.model.Table;
import horenso.persistence.entity.HoldemTable;

import java.util.ArrayList;
import java.util.List;

public interface LobbyService {
    List<Table> TABLE_DTO_LIST = new ArrayList<>();

    public List<HoldemTable> getTableList();

    void addTable(Table table);

    void joinTable(int tableId, String userName) throws InvalidTableIdException, FullTableException;
}
