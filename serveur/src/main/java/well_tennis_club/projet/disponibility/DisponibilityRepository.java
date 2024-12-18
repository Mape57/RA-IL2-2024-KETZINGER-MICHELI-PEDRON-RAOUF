package well_tennis_club.projet.disponibility;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilityRepository extends ListCrudRepository<DisponibilityEntity, Long> {
}
