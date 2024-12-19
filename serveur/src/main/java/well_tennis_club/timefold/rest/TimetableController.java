package well_tennis_club.timefold.rest;

import ai.timefold.solver.core.api.score.analysis.ScoreAnalysis;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.SolverJob;
import ai.timefold.solver.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import well_tennis_club.data_structure.SessionConstraint;
import well_tennis_club.data_structure.Timeslot;
import well_tennis_club.data_structure.ValueRange;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.TennisCourt;
import well_tennis_club.timefold.domain.Timetable;
import well_tennis_club.timefold.domain.Trainer;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/solve")
public class TimetableController {
	private static final List<SessionConstraint> sessionConstraints = List.of(
			// ages,		levels,		grpSizes,	ageDif,		lvlDif,		duration
			// (3, 4),		null,		(4, 6),		null,		null,		60
			new SessionConstraint(new ValueRange(3, 4), null, new ValueRange(4, 6), null, null, 60),
			// (5, 7),		null,		(4, 6),		null,		1,			60
			new SessionConstraint(new ValueRange(5, 7), null, new ValueRange(4, 6), null, 1, 60),
			// (8, 17),		(0, 7),		(3, 6),		2,			1,			60
			new SessionConstraint(new ValueRange(8, 17), new ValueRange(0, 7), new ValueRange(3, 6), 2, 1, 60),
			// (8, 17),		(8, 19),	(3, 6),		2,			1,			90
			new SessionConstraint(new ValueRange(8, 17), new ValueRange(8, 19), new ValueRange(3, 6), 2, 1, 90),
			// (18, 99),	null,		(3, 6),		null,		1,			90
			new SessionConstraint(new ValueRange(18, 99), null, new ValueRange(3, 6), null, 1, 90)
	);

	@Autowired
	private SolverManager<Timetable, UUID> solverManager;

	@Autowired
	private SolutionManager<Timetable, HardSoftScore> solutionManager;

	@GetMapping("")
	public String solve() {
		UUID problemId = UUID.randomUUID();

		SolverJob<Timetable, UUID> solverJob = solverManager.solve(problemId, getProblem());

		Timetable solution;
		try {
			solution = solverJob.getFinalBestSolution();
		} catch (InterruptedException | ExecutionException e) {
			throw new IllegalStateException("Erreur lors de la résolution du problème", e);
		}

		ScoreAnalysis<HardSoftScore> scoreAnalysis = solutionManager.analyze(solution);

		List<String> brokenCosntraints = new ArrayList<>();

		scoreAnalysis.constraintMap().forEach((_, constraintAnalysis) -> {
			if (constraintAnalysis.matches() == null) return;
			constraintAnalysis.matches().forEach(matchAnalysis -> {
				Object justification = matchAnalysis.justification();
				brokenCosntraints.add("Justification : " + justification);
				// brokenCosntraints.add("Constraint : " + matchAnalysis.constraintRef());
			});
		});

		// brokenCosntraints : liste des différentes contraintes non respectées
		// solution : solution du problème

		return solution + "\n" + String.join("\n", brokenCosntraints);
	}

	public static Timetable getProblem() {
		List<Trainer> trainers = List.of(
				new Trainer("trainer1", new ValueRange(0, 18), new ValueRange(0, 10), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
				), false),
				new Trainer("trainer2", new ValueRange(0, 18), new ValueRange(0, 10), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
				), false)
		);
		List<Player> players = List.of(
				new Player("player01", 8, 5, 1, null, sessionConstraints.get(2)),
				new Player("player02", 8, 5, 1, null, sessionConstraints.get(2)),
				new Player("player03", 8, 5, 1, null, sessionConstraints.get(2)),
				new Player("player04", 8, 5, 1, null, sessionConstraints.get(2)),
				new Player("player05", 8, 5, 1, null, sessionConstraints.get(2)),
				new Player("player06", 8, 5, 1, null, sessionConstraints.get(2)),
				new Player("player07", 8, 5, 1, null, sessionConstraints.get(2)),
				new Player("player08", 8, 5, 1, null, sessionConstraints.get(2)),
				new Player("player09", 13, 7, 1, null, sessionConstraints.get(2)),
				new Player("player10", 12, 7, 1, null, sessionConstraints.get(2)),
				new Player("player11", 11, 6, 1, null, sessionConstraints.get(2)),
				new Player("player12", 16, 3, 1, null, sessionConstraints.get(2)),
				new Player("player13", 17, 2, 1, null, sessionConstraints.get(2)),
				new Player("player14", 17, 3, 1, null, sessionConstraints.get(2))
		);
		List<TennisCourt> tennisCourts = List.of(
				new TennisCourt("Terrain 1", List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0))
				)),
				new TennisCourt("Terrain 2", List.of(
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(10, 0))
				))
		);

		return new Timetable(players, trainers, tennisCourts);
	}
}
