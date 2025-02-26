package well_tennis_club.projet.core.player.repository;

import org.springframework.data.repository.ListCrudRepository;
import well_tennis_club.projet.core.player.entity.InscriptionTokenEntity;

import java.util.UUID;

public interface InscriptionTokenRepository extends ListCrudRepository<InscriptionTokenEntity, UUID> {
	InscriptionTokenEntity findByToken(String token);

	void deleteByToken(String token);
}
