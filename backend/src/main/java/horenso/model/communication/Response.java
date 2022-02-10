package horenso.model.communication;

import lombok.Builder;

@Builder
public class Response {
    public final String dest;
    public final String result;
    public String token = null;

    public Response(String dest, String result) {
        this.dest = dest;
        this.result = result;
    }

    public Response(String dest, String result, String token) {
        this.dest = dest;
        this.result = result;
        this.token = token;
    }
}
