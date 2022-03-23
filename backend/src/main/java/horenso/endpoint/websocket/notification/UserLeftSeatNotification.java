package horenso.endpoint.websocket.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLeftSeatNotification extends Notification {
    private long id;
    private String username;
    private short seat;

    public UserLeftSeatNotification(long id, String username, short seat) {
        super("user_left_seat", true);
        this.id = id;
        this.username = username;
        this.seat = seat;
    }
}
