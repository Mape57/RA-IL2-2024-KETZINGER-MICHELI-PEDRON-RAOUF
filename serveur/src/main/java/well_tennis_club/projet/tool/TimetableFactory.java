package well_tennis_club.projet.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import well_tennis_club.projet.core.DEPRECATED_participation.ParticipationService;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.SessionService;
import well_tennis_club.projet.core.solver.TimetableService;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.domain.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TimetableFactory {
	private final TimetableService timetableService;
	private final SessionService sessionService;
	// FIXME trouver un moyen de ne pas avoir à injecter cette dépendance
	private final ParticipationService participationService;

	@Autowired
	public TimetableFactory(TimetableService timetableService, SessionService sessionService, ParticipationService participationService) {
		this.timetableService = timetableService;
		this.sessionService = sessionService;
		this.participationService = participationService;
	}

	public Timetable createTimetable() {
		List<SessionConstraint> sessionConstraints = timetableService.getAllSessionConstraints();
		List<Player> players = timetableService.getAllPlayers();
		players.forEach(player -> setSessionConstraint(player, sessionConstraints));

		List<Trainer> trainers = timetableService.getAllTrainers();
		List<TennisCourt> tennisCourts = timetableService.getAllTennisCourts();

		List<Session> plannedSessions = sessionService.getAllSessions().stream()
				.map(sessionEntity -> {
					if (sessionEntity.getIdTrainer() == null) return convertFrom(sessionEntity, null);
					Trainer trainer = trainers.stream()
							.filter(t -> t.getId().equals(sessionEntity.getIdTrainer().getId()))
							.findFirst()
							.orElseThrow();
					return convertFrom(sessionEntity, trainer);
				})
				.toList();

		if (plannedSessions.isEmpty()) return new Timetable(players, trainers, tennisCourts);

		List<Session> sessions = combineWithNoDuplicate(plannedSessions, Timetable.generateSessions(tennisCourts));

		List<PlayerSessionLink> psls = participationService.getAllParticipation().stream()
				.map(participationEntity -> {
					Player player = players.stream()
							.filter(p -> p.getId().equals(participationEntity.getParticipationKey().getIdPlayer()))
							.findFirst()
							.orElseThrow();
					Session session = plannedSessions.stream()
							.filter(s -> s.getId().equals(participationEntity.getParticipationKey().getIdSession()))
							.findFirst()
							.orElseThrow();
					return new PlayerSessionLink(player, session);
				}).toList();

		if (psls.isEmpty()) return new Timetable("WTC from sessions", players, trainers, sessions);
		else return new Timetable("WTC from sessions and psls", players, trainers, sessions, psls);
	}

	private static void setSessionConstraint(Player player, List<SessionConstraint> sessionConstraints) {
		for (SessionConstraint sessionConstraint : sessionConstraints) {
			if (player.complyWith(sessionConstraint)) {
				player.setSessionConstraint(sessionConstraint);
				return;
			}
		}
	}

	private static Session convertFrom(SessionEntity sessionEntity, Trainer trainer) {
		UUID id = sessionEntity.getId();
		UUID courtId = sessionEntity.getIdCourt().getId();
		DayOfWeek day = DayOfWeek.of(sessionEntity.getDayWeek());
		LocalTime startTime = LocalTime.parse(sessionEntity.getStart());

		return new Session(id, day, startTime, courtId, trainer);
	}

	private static List<Session> combineWithNoDuplicate(List<Session> baseList, List<Session> sessions) {
		List<Session> resultSessions = new ArrayList<>(baseList);
		sessions.forEach(session -> {
			if (!isDuplicate(session, baseList)) {
				resultSessions.add(session);
			}
		});
		return resultSessions;
	}

	private static boolean isDuplicate(Session session, List<Session> sessions) {
		for (Session s : sessions) {
			if (s.getTennisCourt().equals(session.getTennisCourt())
					&& s.getDay().equals(session.getDay())
					&& s.getStartTime().equals(session.getStartTime())) {
				return true;
			}
		}
		return false;
	}
}
