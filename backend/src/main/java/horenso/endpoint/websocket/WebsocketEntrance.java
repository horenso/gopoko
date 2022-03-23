package horenso.endpoint.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import horenso.endpoint.websocket.notification.ErrorNotification;
import horenso.endpoint.websocket.notification.ObservingTableNotification;
import horenso.endpoint.websocket.notification.TableListNotification;
import horenso.endpoint.websocket.request.ChatMessageRequest;
import horenso.endpoint.websocket.request.ObservingRequest;
import horenso.endpoint.websocket.request.TakeSeatRequest;
import horenso.exceptions.ErrorResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

/**
 * This is the entrance were very every Websocket message lands. This class then delegates
 * requests to different endpoints.
 */

@RequiredArgsConstructor
@Component
public class WebsocketEntrance extends TextWebSocketHandler {
    private final LobbyEndpoint lobbyEndpoint;
    private final TableEndpoint tableEndpoint;
    private final Gson gson;
    private final WebsocketSessionManager websocketSessionManager;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String jsonString = message.getPayload();
        Map<String, Object> map;
        try {
            map = gson.fromJson(jsonString, Map.class);
        } catch (JsonSyntaxException e) {
            websocketSessionManager.sendToOneSession(session, new ErrorNotification("/", "invalid JSON"));
            session.close();
            return;
        }
        System.out.println("<< " + gson.toJson(map));
        String dest = map.getOrDefault("dest", null).toString();
        try {
            if (dest == null) {
                throw new ErrorResponseException("/", "field \"dest\" is missing", true);
            }
            switch (dest) {
                case "get_table_list" -> {
                    TableListNotification notification = lobbyEndpoint.getTableList();
                    websocketSessionManager.sendToOneSession(session, notification);
                }
                case "create_table" -> {
                    lobbyEndpoint.createTable();
                }
                case "start_observing_table" -> {
                    ObservingTableNotification notification =
                        tableEndpoint.startObservingTable(session, gson.fromJson(jsonString, ObservingRequest.class));
                    websocketSessionManager.sendToOneSession(session, notification);
                }
                case "stop_observing_table" -> {
                    tableEndpoint.stopObservingTable(session, gson.fromJson(jsonString, ObservingRequest.class));
                }
                case "take_seat" -> {
                    tableEndpoint.takeSeat(session, gson.fromJson(jsonString, TakeSeatRequest.class));
                }
                case "send_message" -> {
                    tableEndpoint.sendMessage(session, gson.fromJson(jsonString, ChatMessageRequest.class));
                }
                default -> throw new ErrorResponseException("/", String.format("dest \"%s\" invalid", dest), true);
            }
        } catch (ErrorResponseException e) {
            websocketSessionManager.sendToOneSession(session, new ErrorNotification(e.getDest(), e.getError()));
            if (e.isDisconnect()) {
                session.close();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connection has been established");
        websocketSessionManager.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        websocketSessionManager.removeSession(session);
    }
}
