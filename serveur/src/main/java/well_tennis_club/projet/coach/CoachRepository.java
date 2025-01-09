package well_tennis_club.projet.coach;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoachRepository extends ListCrudRepository<CoachEntity, UUID> {
    CoachEntity findByEmail(String email);
}
