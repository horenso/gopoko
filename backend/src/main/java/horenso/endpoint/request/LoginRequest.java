package horenso.endpoint.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest extends Request {
    private String username;

    public LoginRequest(String dest, String username) {
        super(dest);
        this.username = username;
    }
}
