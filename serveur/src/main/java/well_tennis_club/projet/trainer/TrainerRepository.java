package well_tennis_club.projet.trainer;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrainerRepository extends ListCrudRepository<TrainerEntity, UUID> {
    TrainerEntity findByEmail(String email);
}
