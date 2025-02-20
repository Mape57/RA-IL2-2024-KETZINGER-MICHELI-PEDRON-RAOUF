package well_tennis_club.projet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.entity.SessionEntity;
import well_tennis_club.projet.repository.SessionRepository;

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

	public void deleteSession(SessionEntity entity) {
		sessionRepository.delete(entity);
	}

	public SessionEntity getSessionById(UUID id) {
		return sessionRepository.findById(id).orElse(null);
	}
}
