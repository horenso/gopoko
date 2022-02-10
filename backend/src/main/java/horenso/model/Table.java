package horenso.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder()
public class Table {
    List<Player> players = new ArrayList<>();
    private int id;
    private String name;
    private String description;
    private int playerMax;
}
