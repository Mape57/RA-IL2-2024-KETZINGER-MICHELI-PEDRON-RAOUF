package well_tennis_club.projet.time;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends ListCrudRepository<TimeEntity, Long> {
}
