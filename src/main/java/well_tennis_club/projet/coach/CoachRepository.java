package well_tennis_club.projet.coach;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends ListCrudRepository<CoachEntity,Long> {
}
