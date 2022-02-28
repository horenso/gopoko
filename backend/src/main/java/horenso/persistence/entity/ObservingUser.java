package horenso.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ObservingUser implements Serializable {
    @EmbeddedId
    private ObservingUserKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("holdemTableId")
    private HoldemTable holdemTable;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @Column(name = "seat_number")
    private Short seatNumber;
}
