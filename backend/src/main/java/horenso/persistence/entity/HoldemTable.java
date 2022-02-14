package horenso.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class HoldemTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
}
