package horenso.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This exception will be thrown by the endpoints if a request in invalid. The disconnect field
 * determines whether the server should disconnect from the client.
 */
@Getter
@AllArgsConstructor
public class ErrorResponseException extends Exception {
    private final String dest;
    private final String error;
    private final boolean disconnect;
}
