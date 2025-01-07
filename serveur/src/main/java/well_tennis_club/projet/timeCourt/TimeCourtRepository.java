package well_tennis_club.projet.timeCourt;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeCourtRepository extends ListCrudRepository<TimeCourtEntity, UUID> {
    List<TimeCourtEntity> findByTimeCourtKeyIdCourt(UUID idCourt);
}
