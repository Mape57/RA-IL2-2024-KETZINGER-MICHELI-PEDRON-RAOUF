package well_tennis_club.projet.disponibilityCoach;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisponibilityCoachRepository extends ListCrudRepository<DisponibilityCoachEntity, UUID> {
    List<DisponibilityCoachEntity> findByDisponibilityCoachKeyIdCoach(UUID idCoach);
}
