package horenso.model.communication;

import horenso.model.Table;

import java.util.List;

public class LobbyUpdateResponse extends Response {
    private List<Table> tables;

    public LobbyUpdateResponse(List<Table> tables) {
        super("tableUpdate", "success");
        this.tables = tables;
    }

    public LobbyUpdateResponse(String destination, String result) {
        super(destination, result);
    }

    public LobbyUpdateResponse(String destination, String result, String token) {
        super(destination, result, token);
    }
}
