package well_tennis_club.timefold.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import lombok.Setter;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.util.*;

@Getter
@PlanningSolution
public class Timetable {
	private String name;

	public static final int MINIMUM_DURATION = 30;

	// ===== PROBLEM FACTS ===== //
	@ProblemFactCollectionProperty
	@ValueRangeProvider
	private List<Trainer> trainers;

	@ProblemFactCollectionProperty
	private List<Player> players;

	// ===== PLANNING ENTITIES ===== //
	@ValueRangeProvider
	@PlanningEntityCollectionProperty
	private List<Session> sessions;

	@PlanningEntityCollectionProperty
	private List<PlayerSessionLink> playerSessionLinks;

	// ===== PLANNING SCORE ===== //
	@Setter
	@PlanningScore
	private HardSoftScore score;

	public Timetable() {
	}

	public Timetable(String name, HardSoftScore score) {
		this.name = name;
		this.score = score;
	}

	public Timetable(List<Player> players, List<Trainer> trainers, List<TennisCourt> tennisCourts) {
		this.name = "WTC";
		this.trainers = trainers;
		this.players = players;
		this.sessions = generateSessions(tennisCourts);
		this.playerSessionLinks = generatePlayerSessionLinks(players);
	}

	public Timetable(String name, List<Player> players, List<Trainer> trainers, List<Session> sessions) {
		this.name = name;
		this.trainers = trainers;
		this.players = players;
		this.sessions = sessions;
		this.playerSessionLinks = generatePlayerSessionLinks(players);
	}

	public Timetable(String name, List<Player> players, List<Trainer> trainers, List<Session> sessions, List<PlayerSessionLink> playerSessionLinks) {
		this.name = name;
		this.trainers = trainers;
		this.players = players;
		this.sessions = sessions;
		this.playerSessionLinks = playerSessionLinks;
	}

	// TODO tester la méthode
	public static List<Session> generateSessions(List<TennisCourt> tennisCourts) {
		List<Session> sessions = new ArrayList<>();
		for (TennisCourt tennisCourt : tennisCourts) {
			for (Timeslot openingHour : tennisCourt.getOpeningHours()) {
				for (int i = 0; i < openingHour.endTime().toSecondOfDay() - openingHour.startTime().toSecondOfDay(); i += MINIMUM_DURATION * 60) {
					sessions.add(new Session(UUID.randomUUID(), openingHour.day(), openingHour.startTime().plusSeconds(i), tennisCourt.getId()));
				}
			}
		}
		return sessions;
	}

	// TODO tester la méthode
	public static List<PlayerSessionLink> generatePlayerSessionLinks(List<Player> players) {
		List<PlayerSessionLink> playerSessionLinks = new ArrayList<>();
		for (Player player : players) {
			for (int i = 0; i < player.getSessionPerWeek(); i++) {
				playerSessionLinks.add(new PlayerSessionLink(player));
			}
		}
		return playerSessionLinks;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Timetable ").append(name).append(" with score ").append(score).append("\n");
		sb.append("Sessions:\n");
		for (Session session : sessions) {
			if (session.getTrainer() == null) {
				sb.append("\t").append(session).append("\n");
			} else {
				sb.append("\t").append(session).append(" -> ").append(session.getTrainer().getId()).append("\n");
			}
		}
		sb.append("PlayerSessionLinks:\n");
		playerSessionLinks.sort(Comparator.comparing(o -> o.getPlayer().getId()));
		for (PlayerSessionLink playerSessionLink : playerSessionLinks) {
			Player player = playerSessionLink.getPlayer();
			sb.append("\t")
					.append(player.getId())
					.append("(").append(player.getAge())
					.append(", ").append(player.getLevel())
					.append(") -> ").append(playerSessionLink.getSession())
					.append("\n");
		}
		sb.append("Score: ").append(score == null ? "null" : score.toString()).append("\n");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Timetable timetable = (Timetable) o;

		if (!Objects.equals(name, timetable.name)) return false;

		if (trainers == null ^ timetable.trainers == null) return false;
		if (players == null ^ timetable.players == null) return false;
		if (sessions == null ^ timetable.sessions == null) return false;
		if (playerSessionLinks == null ^ timetable.playerSessionLinks == null) return false;

		if (trainers != null && !trainers.containsAll(timetable.trainers)) return false;
		if (players != null && !players.containsAll(timetable.players)) return false;
		if (sessions != null && !sessions.containsAll(timetable.sessions)) return false;
		if (playerSessionLinks != null && !playerSessionLinks.containsAll(timetable.playerSessionLinks)) return false;

		return Objects.equals(score, timetable.score);
	}
}
