package well_tennis_club.projet.court;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends ListCrudRepository<CourtEntity,Long> {
}
