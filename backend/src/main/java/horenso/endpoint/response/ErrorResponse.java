package horenso.endpoint.response;

public class ErrorResponse extends Response {
    private final String error;

    public ErrorResponse(String dest, String error) {
        super(dest, false);
        this.error = error;
    }
}
