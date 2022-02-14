package horenso.persistence.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Schema
 * User: [id, name, token]
 * HoldemTable: [id, name]
 * Seated User: [user_id, table_id, seat_number]
 */

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String token;
}
