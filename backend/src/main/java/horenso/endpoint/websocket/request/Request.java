package horenso.endpoint.websocket.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Request {
    private String dest;
}
