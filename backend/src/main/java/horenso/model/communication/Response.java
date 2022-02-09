package horenso.model.communication;

import lombok.Builder;

@Builder
public class Response {
    public final String destination;
    public final String result;
    public String token = null;

    public Response(String destination, String result) {
        this.destination = destination;
        this.result = result;
    }

    public Response(String destination, String result, String token) {
        this.destination = destination;
        this.result = result;
        this.token = token;
    }
}
