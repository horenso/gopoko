package horenso.endpoint.websocket.notification;

public class ErrorNotification extends Notification {
    private final String error;

    public ErrorNotification(String dest, String error) {
        super(dest, false);
        this.error = error;
    }
}
