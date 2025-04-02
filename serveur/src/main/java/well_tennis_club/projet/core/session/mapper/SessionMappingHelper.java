package well_tennis_club.projet.core.session.mapper;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import well_tennis_club.projet.core.court.CourtEntity;
import well_tennis_club.projet.core.court.CourtRepository;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.player.repository.PlayerRepository;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.projet.core.trainer.repository.TrainerRepository;
import well_tennis_club.projet.exception.IdNotFoundException;

import java.util.List;
import java.util.UUID;

@Component
public class SessionMappingHelper {
	private final TrainerRepository trainerRepository;
	private final CourtRepository courtRepository;
	private final PlayerRepository playerRepository;

	@Autowired
	public SessionMappingHelper(TrainerRepository trainerRepository,
								CourtRepository courtRepository,
								PlayerRepository playerRepository) {
		this.trainerRepository = trainerRepository;
		this.courtRepository = courtRepository;
		this.playerRepository = playerRepository;
	}

	@Named(value = "trainerFromId")
	public TrainerEntity trainerFromId(UUID idTrainer) {
		if (idTrainer == null) return null;
		return trainerRepository.findById(idTrainer)
				.orElseThrow(() -> new IdNotFoundException("Pas d'entraineur avec cet id"));
	}

	@Named(value = "courtFromId")
	public CourtEntity courtFromId(UUID idCourt) {
		return courtRepository.findById(idCourt)
				.orElseThrow(() -> new IdNotFoundException("Pas de court avec cet id"));
	}

	@Named(value = "playersFromIds")
	public List<PlayerEntity> playersFromIds(List<UUID> playerIds) {
		List<PlayerEntity> players = playerRepository.findAllById(playerIds);
		if (players.size() != playerIds.size()) {
			throw new IdNotFoundException("Un ou plusieurs joueurs n'ont pas été trouvés");
		}
		return players;
	}

	@Named(value = "playersToIds")
	public List<UUID> playersToIds(List<PlayerEntity> players) {
		return List.of(players.stream().map(PlayerEntity::getId).toArray(UUID[]::new));
	}
}
