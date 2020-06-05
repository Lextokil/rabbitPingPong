package core.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PongRepository extends MongoRepository<PongMongo, String> {
}
