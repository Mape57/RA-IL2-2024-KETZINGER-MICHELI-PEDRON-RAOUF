package well_tennis_club.projet.timeCourt;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeCourtRepository extends ListCrudRepository<TimeCourtEntity,Long> {
    List<TimeCourtEntity> findByTimeCourtKeyIdCourt(Long idCourt);
}
