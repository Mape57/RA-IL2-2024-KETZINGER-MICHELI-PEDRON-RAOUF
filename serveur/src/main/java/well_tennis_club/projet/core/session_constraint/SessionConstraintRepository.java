package well_tennis_club.projet.core.session_constraint;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionConstraintRepository extends ListCrudRepository<SessionConstraintEntity, UUID> {
	int deleteSessionConstraintEntityById(UUID id);
}
