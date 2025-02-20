package well_tennis_club.projet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import well_tennis_club.data_converter.to_domain.PlayerConverter;
import well_tennis_club.data_converter.to_domain.SessionConstraintConverter;
import well_tennis_club.data_converter.to_domain.TennisCourtConverter;
import well_tennis_club.data_converter.to_domain.TrainerConverter;
import well_tennis_club.projet.entity.*;
import well_tennis_club.projet.repository.CourtRepository;
import well_tennis_club.projet.repository.PlayerRepository;
import well_tennis_club.projet.repository.SessionRepository;
import well_tennis_club.projet.repository.TrainerRepository;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.TennisCourt;
import well_tennis_club.timefold.domain.Trainer;

import java.util.List;

@Service
public class TimetableService {
	private final TrainerRepository trainerRepository;
	private final PlayerRepository playerRepository;
	private final CourtRepository courtRepository;
	private final SessionConstraintService sessionConstraintService;
	private final SessionRepository sessionRepository;

	@Autowired
	public TimetableService(TrainerRepository trainerRepository, PlayerRepository playerRepository, CourtRepository courtRepository, SessionConstraintService sessionConstraintService, SessionRepository sessionRepository) {
		this.trainerRepository = trainerRepository;
		this.playerRepository = playerRepository;
		this.courtRepository = courtRepository;
		this.sessionConstraintService = sessionConstraintService;
		this.sessionRepository = sessionRepository;
	}

	public List<Player> getAllPlayers() {
		List<PlayerEntity> playerEntities = playerRepository.findAll();
		return playerEntities.stream().map(PlayerConverter::from).toList();
	}

	public List<Trainer> getAllTrainers() {
		List<TrainerEntity> trainerEntities = trainerRepository.findAll();
		return trainerEntities.stream().map(TrainerConverter::from).toList();
	}

	public List<TennisCourt> getAllTennisCourts() {
		List<CourtEntity> courtEntities = courtRepository.findAll();
		return courtEntities.stream().map(TennisCourtConverter::from).toList();
	}

	public List<SessionConstraint> getAllSessionConstraints() {
		List<SessionConstraintEntity> sessionConstraintEntities = sessionConstraintService.getAllConstraints();
		return sessionConstraintEntities.stream().map(SessionConstraintConverter::from).toList();
	}

	public void saveAllPlayer(List<PlayerEntity> players) {
		playerRepository.saveAll(players);
	}

	public void saveAllTrainer(List<TrainerEntity> trainers) {
		trainerRepository.saveAll(trainers);
	}

	public void saveAllCourt(List<CourtEntity> courts) {
		courtRepository.saveAll(courts);
	}

	public void saveAllSessionConstraint(List<SessionConstraintEntity> sessionConstraints) {
		for (SessionConstraintEntity sessionConstraint : sessionConstraints) {
			sessionConstraintService.createConstraint(sessionConstraint);
		}
	}

	public void saveAllSession(List<SessionEntity> sessionEntities) {
		sessionRepository.saveAll(sessionEntities);
	}
}
