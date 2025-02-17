package well_tennis_club.timefold.application;

import ai.timefold.solver.core.api.score.analysis.ScoreAnalysis;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.*;
import ai.timefold.solver.core.config.solver.SolverConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import well_tennis_club.timefold.domain.Timetable;
import well_tennis_club.timefold.tools.SampleData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/old-timetable")
public class OldTimetableController {
	@Autowired
	private SolverManager<Timetable, UUID> solverManager;

	@Autowired
	private SolutionManager<Timetable, HardSoftScore> solutionManager;

	@PostMapping("/solve")
	public Timetable solve(@RequestBody Timetable timetable) {
		UUID problemId = UUID.randomUUID();

		SolverJob<Timetable, UUID> solverJob = solverManager.solve(problemId, timetable);

		Timetable solution;
		try {
			solution = solverJob.getFinalBestSolution();
		} catch (InterruptedException | ExecutionException e) {
			throw new IllegalStateException("Erreur lors de la résolution du problème", e);
		}

		ScoreAnalysis<HardSoftScore> scoreAnalysis = solutionManager.analyze(solution);

		List<String> brokenCosntraints = new ArrayList<>();

		scoreAnalysis.constraintMap().forEach((x, constraintAnalysis) -> {
			if (constraintAnalysis.matches() == null) return;
			constraintAnalysis.matches().forEach(matchAnalysis -> {
				Object justification = matchAnalysis.justification();
				brokenCosntraints.add("Justification : " + justification);
				// brokenCosntraints.add("Constraint : " + matchAnalysis.constraintRef());
			});
		});

		// brokenCosntraints : liste des différentes contraintes non respectées
		// solution : solution du problème

		System.out.println(solution);
		System.out.println(String.join("\n", brokenCosntraints));

		return solution;
	}

	@GetMapping("")
	public Timetable get() {
		return getProblem();
	}

	public static Timetable getProblem() {
		return new Timetable(SampleData.players.real_3_4, SampleData.trainers.real(), SampleData.tennisCourts.real);
	}


	@PostMapping("/real")
	public Timetable real(@RequestBody Timetable timetable) {
		SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
		SolverFactory<Timetable> solverFactory = SolverFactory.create(solverConfig);

		Solver<Timetable> solver = solverFactory.buildSolver();

		long startTime = System.nanoTime();
		solver.addEventListener(event -> {
			Timetable newBestSolution = event.getNewBestSolution();
			System.out.println("New best solution found with score: " + newBestSolution.getScore().toString() + " after " + (System.nanoTime() - startTime) + " ns");
			//System.out.println(newBestSolution);
		});

		Timetable solution = solver.solve(timetable);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		System.out.println("Solution found in " + TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS) + " seconds");
		System.out.println(solution);

		// Zone d'analyse de la solution
		SolutionManager<Timetable, HardSoftScore> solutionManager = SolutionManager.create(solverFactory);
		ScoreAnalysis<HardSoftScore> scoreAnalysis = solutionManager.analyze(solution);

		List<String> brokenCosntraints = new ArrayList<>();

		scoreAnalysis.constraintMap().forEach((x, constraintAnalysis) -> {
			if (constraintAnalysis.matches() == null) return;
			constraintAnalysis.matches().forEach(matchAnalysis -> {
				Object justification = matchAnalysis.justification();
				brokenCosntraints.add("Justification : " + justification);
				// brokenCosntraints.add("Constraint : " + matchAnalysis.constraintRef());
			});
		});

		System.out.println("Broken constraints:");
		brokenCosntraints.forEach(System.out::println);

		return solution;
	}
}