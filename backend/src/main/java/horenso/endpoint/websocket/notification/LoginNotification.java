package horenso.endpoint.websocket.notification;

public class LoginNotification extends Notification {
    private final String username;
    private final String token;

    public LoginNotification(String dest, boolean success, String username, String token) {
        super("login", true);
        this.username = username;
        this.token = token;
    }
}
