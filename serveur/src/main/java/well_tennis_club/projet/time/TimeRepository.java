package well_tennis_club.projet.time;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimeRepository extends ListCrudRepository<TimeEntity, UUID> {
}
