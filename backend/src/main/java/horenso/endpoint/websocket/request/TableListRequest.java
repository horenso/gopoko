package horenso.endpoint.websocket.request;

import lombok.Getter;

@Getter
public class TableListRequest extends Request {
    public TableListRequest(String dest) {
        super(dest);
    }
}
