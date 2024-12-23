package well_tennis_club.projet.disponibilityCoach;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilityCoachRepository extends ListCrudRepository<DisponibilityCoachEntity, Long> {
    List<DisponibilityCoachEntity> findByDisponibilityCoachKeyIdCoach(Long idCoach);
}
