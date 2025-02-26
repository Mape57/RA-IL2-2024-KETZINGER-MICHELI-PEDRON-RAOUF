package well_tennis_club.projet.core.player.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import well_tennis_club.projet.core.player.entity.PlayerEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends ListCrudRepository<PlayerEntity, UUID> {
	List<PlayerEntity> findByValidate(Boolean validate);

	int deletePlayerEntityById(UUID id);
}
