package horenso.endpoint.websocket.request;

import lombok.Getter;

@Getter
public class ObservingRequest extends Request {
    private final long id;

    public ObservingRequest(int id) {
        super("observing");
        this.id = id;
    }
}
