package well_tennis_club.timefold.application;

import ai.timefold.solver.core.api.score.analysis.ScoreAnalysis;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import well_tennis_club.timefold.domain.Timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static well_tennis_club.timefold.application.TimetableController.getProblem;

public class BasicApp {
	private static long bestTime = Long.MAX_VALUE;
	private static long worstTime = Long.MIN_VALUE;
	private static long averageTime = 0;

	// == 27571: (at: 0.293054516 s) - (wt: 0.764513300 s) - (bt: 0.169408300 s) == //
	public static void main(String[] args) {
		int i = -1; // -1 for one time execution, 0 for  executions
		if (i == -1) run(true);
		else while (i++ != -1) {
			long currentTime = run();
			if (currentTime > worstTime) {
				worstTime = currentTime;
			}
			if (currentTime < bestTime) {
				bestTime = currentTime;
			}

			averageTime = (averageTime * i + currentTime) / (i + 1);
			System.out.println(i + ": (at:" + averageTime + " ns) - (wt: " + worstTime + " ns) - (bt: " + bestTime + " ns)");
		}
	}

	private static long run() {
		return run(false);
	}

	private static long run(boolean print) {
		SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
		SolverFactory<Timetable> solverFactory = SolverFactory.create(solverConfig);
		Timetable problem = getProblem();
		Solver<Timetable> solver = solverFactory.buildSolver();

		long startTime = System.nanoTime();
		solver.addEventListener(event -> {
			Timetable newBestSolution = event.getNewBestSolution();
			System.out.println("New best solution found with score: " + newBestSolution.getScore().toString() + " after " + (System.nanoTime() - startTime) + " ns");
			//System.out.println(newBestSolution);
		});

		Timetable solution = solver.solve(problem);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		if (print) {
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
		}
		return duration;
	}
}
