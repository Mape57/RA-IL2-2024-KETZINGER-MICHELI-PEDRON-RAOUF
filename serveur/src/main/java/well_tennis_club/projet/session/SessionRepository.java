package well_tennis_club.projet.session;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends ListCrudRepository<SessionEntity, UUID> {
}
