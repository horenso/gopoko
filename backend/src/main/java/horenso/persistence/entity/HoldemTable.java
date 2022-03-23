package horenso.persistence.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
public class HoldemTable implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Range(min = 2, max = 10)
    private int numberOfUsers;

    @OneToMany(mappedBy = "holdemTable")
    private Set<ObservingUser> observingUsers;
}
