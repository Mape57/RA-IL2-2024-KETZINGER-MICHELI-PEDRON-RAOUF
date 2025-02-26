package well_tennis_club.projet.core.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {
	private final SessionRepository sessionRepository;

	@Autowired
	public SessionService(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	public List<SessionEntity> getAllSessions() {
		return sessionRepository.findAll();
	}

	public SessionEntity createSession(SessionEntity entity) {
		return sessionRepository.save(entity);
	}

	public SessionEntity updateSession(SessionEntity entity) {
		return sessionRepository.save(entity);
	}

	public int deleteById(UUID id) {
		return sessionRepository.deleteSessionEntityById(id);
	}

	public SessionEntity getSessionById(UUID id) {
		return sessionRepository.findById(id).orElse(null);
	}
}
