package horenso.endpoint;

import com.google.gson.Gson;
import horenso.model.communication.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WebsocketHelper {
    private final Gson gson;

    public void sendResponse(Response response, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(gson.toJson(response)));
    }
}
