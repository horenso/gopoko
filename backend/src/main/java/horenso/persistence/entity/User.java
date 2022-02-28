package horenso.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String token;

    @OneToMany(mappedBy = "user")
    private Set<ObservingUser> observingTables;

    public User() {
    }

    public User(String name, String password, String token) {
        this.name = name;
        this.password = password;
        this.token = token;
    }
}
