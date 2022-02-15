package horenso.service.impl;

import horenso.persistence.repository.HoldemTableRepository;
import horenso.service.TableService;
import org.springframework.stereotype.Service;

@Service
public class TableServiceImpl implements TableService {
    private final HoldemTableRepository holdemTableRepository;

    public TableServiceImpl(HoldemTableRepository holdemTableRepository) {
        this.holdemTableRepository = holdemTableRepository;
    }
}
