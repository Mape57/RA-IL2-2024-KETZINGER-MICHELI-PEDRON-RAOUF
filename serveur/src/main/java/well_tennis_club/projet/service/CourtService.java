package well_tennis_club.projet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.entity.CourtEntity;
import well_tennis_club.projet.repository.CourtRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CourtService {
	private final CourtRepository courtRepository;

	@Autowired
	public CourtService(CourtRepository courtRepository) {
		this.courtRepository = courtRepository;
	}

	public List<CourtEntity> getAllCourts() {
		return courtRepository.findAll();
	}

	public CourtEntity createCourt(CourtEntity entity) {
		return courtRepository.save(entity);
	}

	public CourtEntity updateCourt(CourtEntity entity) {
		return courtRepository.save(entity);
	}

	public void deleteCourt(CourtEntity entity) {
		courtRepository.delete(entity);
	}

	public CourtEntity getCourtById(UUID id) {
		return courtRepository.findById(id).orElse(null);
	}
}
