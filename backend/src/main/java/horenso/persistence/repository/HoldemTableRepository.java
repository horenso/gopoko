package horenso.persistence.repository;

import horenso.persistence.entity.HoldemTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoldemTableRepository extends CrudRepository<HoldemTable, Long> {
    List<HoldemTable> findAll();
}
