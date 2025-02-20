package well_tennis_club.projet.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import well_tennis_club.projet.entity.TimeEntity;

import java.util.UUID;

@Repository
public interface TimeRepository extends ListCrudRepository<TimeEntity, UUID> {
}
