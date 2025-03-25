package well_tennis_club.projet.core.session;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends ListCrudRepository<SessionEntity, UUID> {
	int deleteSessionEntityById(UUID id);

	List<SessionEntity> findAllByIdTrainer(TrainerEntity trainer);
}
