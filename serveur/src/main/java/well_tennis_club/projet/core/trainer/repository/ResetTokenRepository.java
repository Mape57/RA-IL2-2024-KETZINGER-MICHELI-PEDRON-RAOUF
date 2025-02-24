package well_tennis_club.projet.core.trainer.repository;

import org.springframework.data.repository.ListCrudRepository;
import well_tennis_club.projet.core.trainer.entity.ResetTokenEntity;

import java.util.UUID;

public interface ResetTokenRepository extends ListCrudRepository<ResetTokenEntity, UUID> {
	ResetTokenEntity findByToken(String token);

	void deleteByToken(String token);
}