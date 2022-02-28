package horenso.persistence.repository;

import horenso.persistence.entity.ObservingUser;
import horenso.persistence.entity.ObservingUserKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservingUserRepository extends CrudRepository<ObservingUser, ObservingUserKey> {
    @Query("select u from ObservingUser u where u.id.holdemTableId = :id")
    List<ObservingUser> findSeatedUsersByHoldemTableId(long id);
}
