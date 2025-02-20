package well_tennis_club.projet.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import well_tennis_club.projet.entity.CourtEntity;

import java.util.UUID;

@Repository
public interface CourtRepository extends ListCrudRepository<CourtEntity, UUID> {
}
