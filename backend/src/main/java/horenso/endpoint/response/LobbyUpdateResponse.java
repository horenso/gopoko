package horenso.endpoint.response;

import horenso.model.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LobbyUpdateResponse extends Response {
    private List<Table> tables;

    public LobbyUpdateResponse(List<Table> tables) {
        super("table_list_update", true);
        this.tables = tables;
    }
}
