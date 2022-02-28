package horenso.persistence.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ObservingUserKey implements Serializable {
    @Column(name = "holdem_table_id")
    private Long holdemTableId;

    @Column(name = "user_id")
    private Long userId;
}
