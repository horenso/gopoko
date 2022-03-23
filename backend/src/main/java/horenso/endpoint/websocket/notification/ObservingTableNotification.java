package horenso.endpoint.websocket.notification;

import horenso.model.SeatedUser;
import horenso.model.TableInfo;

import java.util.List;

public class ObservingTableNotification extends Notification {
    private final int seats;
    private final List<SeatedUser> players;

    public ObservingTableNotification(TableInfo tableInfo) {
        super("start_observing_table", true);
        this.seats = tableInfo.getNumberOfSeats();
        this.players = tableInfo.getPlayers();
    }
}
