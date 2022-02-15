package horenso.endpoint.websocket.response;

import horenso.model.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableListResponse extends Response {
    private List<Table> tables;

    public TableListResponse(List<Table> tables) {
        super("table_list", true);
        this.tables = tables;
    }
}
