package horenso.persistence.repository;

import horenso.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    User save(User user);

    Optional<User> findFirstByName(String name);

    Optional<User> findFirstByNameAndPassword(String name, String password);

    boolean existsByToken(String token);
}
