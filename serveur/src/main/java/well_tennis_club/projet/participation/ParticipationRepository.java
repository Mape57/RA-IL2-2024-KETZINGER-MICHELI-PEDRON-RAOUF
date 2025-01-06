package well_tennis_club.projet.participation;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends ListCrudRepository<ParticipationEntity,Long> {
    List<ParticipationEntity> findByParticipationKeyIdPlayer(Long id);
}
