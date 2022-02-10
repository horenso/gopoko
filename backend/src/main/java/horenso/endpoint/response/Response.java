package horenso.endpoint.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class Response {
    private final String dest;
    private final Boolean success;
}
