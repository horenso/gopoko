package horenso.endpoint.websocket.response;

public class LoginResponse extends Response {
    private final String username;
    private final String token;

    public LoginResponse(String dest, boolean success, String username, String token) {
        super("login", true);
        this.username = username;
        this.token = token;
    }
}
