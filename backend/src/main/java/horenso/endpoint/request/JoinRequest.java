package horenso.endpoint.request;

import lombok.Getter;

@Getter
public class JoinRequest extends Request {
    private final int id;

    public JoinRequest(int id) {
        super("join");
        this.id = id;
    }
}
