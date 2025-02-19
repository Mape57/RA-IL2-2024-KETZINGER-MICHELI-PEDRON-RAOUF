package well_tennis_club.projet.mail;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface ResetTokenRepository extends ListCrudRepository<ResetTokenEntity, UUID> {
	ResetTokenEntity findByToken(String token);
}