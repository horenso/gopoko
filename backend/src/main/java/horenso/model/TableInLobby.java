package horenso.model;

import lombok.Data;

import java.util.UUID;

/**
 * One table entry in the lobby, which a player can join.
 */

@Data
public class TableInLobby {
    private UUID uuid;
    private String name;
    private String description;
    private int maxPlayers;
    private int currentPlayerCount;
}
