package horenso.endpoint.websocket.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class Notification {
    private final String dest;
    private final boolean success;
}
