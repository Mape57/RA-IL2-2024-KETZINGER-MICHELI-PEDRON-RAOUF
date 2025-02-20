package well_tennis_club.projet.inscription;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface InscriptionTokenRepository extends ListCrudRepository<InscriptionTokenEntity, UUID> {
	InscriptionTokenEntity findByToken(String token);
	void deleteByToken(String token);
}
