package well_tennis_club.projet.disponibilityPlayer;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilityPlayerRepository extends ListCrudRepository<DisponibilityPlayerEntity, Long> {
    List<DisponibilityPlayerEntity> findByDisponibilityPlayerKeyIdPlayer(Long idPlayer);
}
