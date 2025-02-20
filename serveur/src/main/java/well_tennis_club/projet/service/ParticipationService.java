package well_tennis_club.projet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.entity.ParticipationEntity;
import well_tennis_club.projet.repository.ParticipationRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipationService {
	private final ParticipationRepository participationRepository;

	@Autowired
	public ParticipationService(ParticipationRepository participationRepository) {
		this.participationRepository = participationRepository;
	}

	public List<ParticipationEntity> getAllParticipation() {
		return participationRepository.findAll();
	}

	public ParticipationEntity createParticipation(ParticipationEntity entity) {
		return participationRepository.save(entity);
	}

	public void deleteParticipation(ParticipationEntity entity) {
		participationRepository.delete(entity);
	}

	public List<ParticipationEntity> getParticipationForPlayer(UUID id) {
		return participationRepository.findByParticipationKeyIdPlayer(id);
	}
}
