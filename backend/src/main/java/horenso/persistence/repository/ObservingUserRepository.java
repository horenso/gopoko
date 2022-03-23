package horenso.persistence.repository;

import horenso.model.ObservingUser;
import horenso.model.SeatedUser;
import horenso.persistence.entity.ObservingUserKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObservingUserRepository extends CrudRepository<horenso.persistence.entity.ObservingUser, ObservingUserKey> {
    @Query("""
              select new horenso.model.SeatedUser(u.id, u.name, o.seatNumber)
              from ObservingUser o
              inner join User u
              on o.id.userId = u.id
              where o.id.holdemTableId = :tableId and
              o.seatNumber is not null
            """)
    List<SeatedUser> getSeatedUsers(long tableId);

    @Query(value = """
              select new horenso.model.SeatedUser(u.id, u.name, o.seatNumber)
              from ObservingUser o
              inner join User u
              on o.id.userId = u.id
              where o.id.holdemTableId = :tableId and
              o.seatNumber = :seat
            """)
    Optional<SeatedUser> getSeatedUser(long tableId, short seat);

//    @Query("""
//                delete from ObservingUser o
//                where o.id = :userId and o.holdemTable = :tableId
//
//            """)
//    void seatUser(long tableId, long userId, short seat);

    @Query("""
              select new horenso.model.ObservingUser(u.id, u.name)
              from ObservingUser o
              inner join User u
              on o.id.userId = u.id
              where o.id.holdemTableId = :tableId
            """)
    List<ObservingUser> getObservingUser(long tableId);
}
