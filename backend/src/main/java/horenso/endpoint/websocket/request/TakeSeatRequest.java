package horenso.endpoint.websocket.request;

import lombok.Getter;

@Getter
public class TakeSeatRequest extends Request {
    private final long id;
    private final short seat;

    public TakeSeatRequest(long id, short seat) {
        super("take_seat");
        this.id = id;
        this.seat = seat;
    }
}
