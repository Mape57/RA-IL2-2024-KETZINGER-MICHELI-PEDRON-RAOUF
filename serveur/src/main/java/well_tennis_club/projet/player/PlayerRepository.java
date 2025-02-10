package well_tennis_club.projet.player;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends ListCrudRepository<PlayerEntity, UUID>{
    List<PlayerEntity> findByValidate(Boolean validate);
}
