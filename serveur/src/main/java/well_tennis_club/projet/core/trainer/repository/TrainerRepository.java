package well_tennis_club.projet.core.trainer.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.util.UUID;

@Repository
public interface TrainerRepository extends ListCrudRepository<TrainerEntity, UUID> {
	TrainerEntity findByEmail(String email);

	int deleteTrainerEntityById(UUID id);
}
