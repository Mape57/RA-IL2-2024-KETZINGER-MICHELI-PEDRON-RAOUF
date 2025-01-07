package well_tennis_club.projet.disponibility;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DisponibilityRepository extends ListCrudRepository<DisponibilityEntity, UUID> {
}
