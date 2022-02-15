package horenso.persistence.repository;

import horenso.persistence.entity.HoldemTable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HoldemTableRepository extends CrudRepository<HoldemTable, Long> {
    List<HoldemTable> findAll();
}
