package well_tennis_club.projet.core.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.player.repository.PlayerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {
	private final PlayerRepository playerRepository;

	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	public List<PlayerEntity> getAllPlayers() {
		return playerRepository.findAll();
	}

	public PlayerEntity createPlayer(PlayerEntity entity) {
		return playerRepository.save(entity);
	}

	public PlayerEntity updatePlayer(PlayerEntity entity) {
		return playerRepository.save(entity);
	}

	public int deleteById(UUID id) {
		return playerRepository.deletePlayerEntityById(id);
	}

	public PlayerEntity getPlayerById(UUID id) {
		return playerRepository.findById(id).orElse(null);
	}

	public List<PlayerEntity> getPlayerValidate(boolean validate) {
		return playerRepository.findByValidate(validate);
	}
}
