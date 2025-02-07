package well_tennis_club.projet.disponibilityTrainer;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisponibilityTrainerRepository extends ListCrudRepository<DisponibilityTrainerEntity, UUID> {
    List<DisponibilityTrainerEntity> findByDisponibilityTrainerKeyIdTrainer(UUID idTrainer);
}
