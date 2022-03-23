package horenso.endpoint.websocket.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSeatedNotification extends Notification {
    private long id;
    private String username;
    private short seat;

    public UserSeatedNotification(long id, String username, short seat) {
        super("user_seated", true);
        this.id = id;
        this.username = username;
        this.seat = seat;
    }
}
