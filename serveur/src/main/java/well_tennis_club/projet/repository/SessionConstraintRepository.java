package well_tennis_club.projet.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import well_tennis_club.projet.entity.SessionConstraintEntity;

import java.util.UUID;

@Repository
public interface SessionConstraintRepository extends ListCrudRepository<SessionConstraintEntity, UUID> {
}
