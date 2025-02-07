package well_tennis_club.projet.player;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends ListCrudRepository<PlayerEntity, UUID>{
}
