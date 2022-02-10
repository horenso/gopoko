package horenso.model.communication;

import horenso.model.Table;

import java.util.List;

public class LobbyUpdateResponse extends Response {
    private List<Table> tables;

    public LobbyUpdateResponse(List<Table> tables) {
        super("table_update", "success");
        this.tables = tables;
    }

    public LobbyUpdateResponse(String dest, String result) {
        super(dest, result);
    }

    public LobbyUpdateResponse(String dest, String result, String token) {
        super(dest, result, token);
    }
}
