package well_tennis_club.projet.core.court;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public int deleteById(UUID id) {
		return courtRepository.deleteCourtEntityById(id);
	}

	public CourtEntity getCourtById(UUID id) {
		return courtRepository.findById(id).orElse(null);
	}
}
