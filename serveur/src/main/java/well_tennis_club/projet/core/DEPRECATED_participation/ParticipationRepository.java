package well_tennis_club.projet.core.DEPRECATED_participation;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Deprecated
public interface ParticipationRepository extends ListCrudRepository<ParticipationEntity, UUID> {
	void deleteByParticipationKey_IdPlayer(UUID participationKeyIdPlayer);
}
