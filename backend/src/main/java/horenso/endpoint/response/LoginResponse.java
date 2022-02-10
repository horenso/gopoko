package horenso.endpoint.response;

public class LoginResponse extends Response {
    private final String token;

    public LoginResponse(String token) {
        super("login", true);
        this.token = token;
    }
}
