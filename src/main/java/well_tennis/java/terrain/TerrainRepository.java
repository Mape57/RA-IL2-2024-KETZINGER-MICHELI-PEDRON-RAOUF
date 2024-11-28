package well_tennis.java.terrain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerrainRepository extends MongoRepository<TerrainEntity, String> {

    List<TerrainEntity> findAll();
}
