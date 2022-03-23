package horenso.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeatedUser extends ObservingUser {
    private short seat;

    public SeatedUser(Long id, String username, short seat) {
        super(id, username);
        this.seat = seat;
    }
}
