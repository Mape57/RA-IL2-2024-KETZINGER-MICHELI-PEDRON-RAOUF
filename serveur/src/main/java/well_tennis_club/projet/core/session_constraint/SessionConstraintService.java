package well_tennis_club.projet.core.session_constraint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionConstraintService {
	private final SessionConstraintRepository sessionConstraintRepository;

	@Autowired
	public SessionConstraintService(SessionConstraintRepository sessionConstraintRepository) {
		this.sessionConstraintRepository = sessionConstraintRepository;
	}

	public List<SessionConstraintEntity> getAllConstraints() {
		return sessionConstraintRepository.findAll();
	}

	public SessionConstraintEntity createConstraint(SessionConstraintEntity entity) {
		return sessionConstraintRepository.save(entity);
	}

	public SessionConstraintEntity updateConstraint(SessionConstraintEntity entity) {
		return sessionConstraintRepository.save(entity);
	}

	public int deleteById(UUID id) {
		return sessionConstraintRepository.deleteSessionConstraintEntityById(id);
	}

	public SessionConstraintEntity getConstraintById(UUID id) {
		return sessionConstraintRepository.findById(id).orElse(null);
	}
}
