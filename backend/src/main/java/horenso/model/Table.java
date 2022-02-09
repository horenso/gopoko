package horenso.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Table {
    private String name;
    private String description;
    private int playerMax;
    List<Player> players = new ArrayList<>();
}
