package well_tennis_club.projet.core.court;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourtRepository extends ListCrudRepository<CourtEntity, UUID> {
	int deleteCourtEntityById(UUID id);
}
