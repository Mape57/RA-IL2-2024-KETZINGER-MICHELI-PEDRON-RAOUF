package well_tennis_club.projet.disponibilityPlayer;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisponibilityPlayerRepository extends ListCrudRepository<DisponibilityPlayerEntity, UUID> {
    List<DisponibilityPlayerEntity> findByDisponibilityPlayerKeyIdPlayer(UUID idPlayer);
}
