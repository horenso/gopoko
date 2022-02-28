package horenso.endpoint.websocket.notification;

import horenso.model.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableListNotification extends Notification {
    private List<Table> tables;

    public TableListNotification(List<Table> tables) {
        super("get_table_list", true);
        this.tables = tables;
    }
}
