package core.dbh2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PongH2Repository extends CrudRepository<PongH2, Long> {
}
