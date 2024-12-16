package well_tennis_club.timefold.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.data_structure.Timeslot;

import java.util.ArrayList;
import java.util.List;

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
	@PlanningScore
	private HardSoftScore score;

	public Timetable() {
	}

	public Timetable(String name, HardSoftScore score) {
		this.name = name;
		this.score = score;
	}

	public Timetable(List<Player> players, List<Trainer> trainers, List<TennisCourt> tennisCourts) {
		this.name = "Timetable - WTC";
		this.trainers = trainers;
		this.players = players;

		this.sessions = new ArrayList<>();
		for (TennisCourt tennisCourt : tennisCourts) {
			for (Timeslot openingHour : tennisCourt.getOpeningHours()) {
				for (int i = 0; i < openingHour.endTime().toSecondOfDay() - openingHour.startTime().toSecondOfDay(); i += MINIMUM_DURATION * 60) {
					this.sessions.add(new Session(openingHour.day(), openingHour.startTime().plusSeconds(i), tennisCourt.getName()));
				}
			}
		}

		this.playerSessionLinks = new ArrayList<>();
		for (Player player : players) {
			for (int i = 0; i < player.getSessionPerWeek(); i++) {
				this.playerSessionLinks.add(new PlayerSessionLink(player));
			}
		}
	}

	public Timetable(String name, List<Session> sessions, List<Trainer> trainers, List<Player> players) {
		this.name = name;
		this.trainers = trainers;
		this.players = players;
		this.sessions = sessions;

		this.playerSessionLinks = new ArrayList<>();
		for (Player player : players) {
			for (int j = 0; j < player.getSessionPerWeek(); j++) {
				this.playerSessionLinks.add(new PlayerSessionLink(player));
			}
		}
	}

	public Timetable(String name, List<Session> sessions, List<Trainer> trainers, List<Player> players, List<PlayerSessionLink> playerSessionLinks) {
		this.name = name;
		this.trainers = trainers;
		this.players = players;
		this.sessions = sessions;
		this.playerSessionLinks = playerSessionLinks;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Timetable ").append(name).append(" with score ").append(score).append("\n");
		sb.append("Sessions:\n");
		for (Session session : sessions) {
			sb.append("\t").append(session).append(" -> ").append(session.getTrainer().getName()).append("\n");
		}
		sb.append("PlayerSessionLinks:\n");
		for (PlayerSessionLink playerSessionLink : playerSessionLinks) {
			Player player = playerSessionLink.getPlayer();
			sb.append("\t")
					.append(player.getName())
					.append("(").append(player.getAge())
					.append(", ").append(player.getLevel())
					.append(") -> ").append(playerSessionLink.getSession())
					.append("\n");
		}
		return sb.toString();
	}
}
