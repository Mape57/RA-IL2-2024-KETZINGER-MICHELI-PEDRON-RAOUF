package well_tennis_club.projet.core.DEPRECATED_participation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Deprecated
public class ParticipationService {
	private final ParticipationRepository participationRepository;

	@Autowired
	public ParticipationService(ParticipationRepository participationRepository) {
		this.participationRepository = participationRepository;
	}

	public List<ParticipationEntity> getAllParticipation() {
		return participationRepository.findAll();
	}

	public void deleteByPlayerId(UUID playerId) {
		participationRepository.deleteByParticipationKey_IdPlayer(playerId);
	}
}
