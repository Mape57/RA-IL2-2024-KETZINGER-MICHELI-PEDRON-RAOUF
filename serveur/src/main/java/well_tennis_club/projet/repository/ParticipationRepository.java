package well_tennis_club.projet.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import well_tennis_club.projet.entity.ParticipationEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends ListCrudRepository<ParticipationEntity, UUID> {
	List<ParticipationEntity> findByParticipationKeyIdPlayer(UUID id);
}
