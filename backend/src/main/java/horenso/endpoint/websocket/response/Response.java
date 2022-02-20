package horenso.endpoint.websocket.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class Response {
    private final String dest;
    private final boolean success;
}