package horenso.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class SeatedUser implements Serializable {
    @EmbeddedId
    private SeatedUserKey id;

    @ManyToOne
    @MapsId("holdemTableId")
    @JoinColumn(name = "holdem_table_id")
    private HoldemTable holdemTable;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private Short seatNumber;
}
