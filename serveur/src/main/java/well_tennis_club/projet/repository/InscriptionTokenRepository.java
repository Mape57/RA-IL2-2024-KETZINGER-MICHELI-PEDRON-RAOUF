package well_tennis_club.projet.repository;

import org.springframework.data.repository.ListCrudRepository;
import well_tennis_club.projet.entity.InscriptionTokenEntity;

import java.util.UUID;

public interface InscriptionTokenRepository extends ListCrudRepository<InscriptionTokenEntity, UUID> {
	InscriptionTokenEntity findByToken(String token);

	void deleteByToken(String token);
}
