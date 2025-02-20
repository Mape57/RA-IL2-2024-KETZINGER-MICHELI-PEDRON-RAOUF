package well_tennis_club.projet.password_reset;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface ResetTokenRepository extends ListCrudRepository<ResetTokenEntity, UUID> {
	ResetTokenEntity findByToken(String token);
	void deleteByToken(String token);
}