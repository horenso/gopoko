package horenso.endpoint;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebsocketHelper {
    private final Gson gson;


}
