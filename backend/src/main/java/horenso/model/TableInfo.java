package horenso.model;

import lombok.Data;

import java.util.List;

@Data
public class TableInfo {
    private int numberOfSeats;
    private List<SeatedUser> players;

    public TableInfo(int numberOfSeats, List<SeatedUser> seatedUserList) {
        this.numberOfSeats = numberOfSeats;
        this.players = seatedUserList;
//        seatMap = new HashMap<>(numberOfSeats);
//        for (SeatedUser s: seatedUserList) {
//            seatMap.put(s.getSeat(), new ObservingUser(s.getId(), s.getUsername()));
//        }
    }
}
