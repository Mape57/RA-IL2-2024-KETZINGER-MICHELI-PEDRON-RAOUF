package well_tennis_club.projet.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import well_tennis_club.projet.entity.DisponibilityTrainerEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisponibilityTrainerRepository extends ListCrudRepository<DisponibilityTrainerEntity, UUID> {
	List<DisponibilityTrainerEntity> findByDisponibilityTrainerKeyIdTrainer(UUID idTrainer);
}
