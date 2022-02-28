package horenso.persistence.repository;

import horenso.persistence.entity.ObservingUser;
import horenso.persistence.entity.ObservingUserKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservingUserRepository extends CrudRepository<ObservingUser, ObservingUserKey> {
    @Query("""
              select u.name
              from ObservingUser o
              inner join User u
              on o.id.userId = u.id
              where o.id.holdemTableId = :tableId
            """)
    List<String> findUsernamesOfSeatedUsers(long tableId);
}
