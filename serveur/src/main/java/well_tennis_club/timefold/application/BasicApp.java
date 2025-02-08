package well_tennis_club.timefold.application;

import ai.timefold.solver.core.api.score.analysis.ScoreAnalysis;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
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
	public static void main(String[] args) {
		// initialisation du solveur
		SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
		SolverFactory<Timetable> solverFactory = SolverFactory.create(solverConfig);
		Timetable problem = getProblem();
		Solver<Timetable> solver = solverFactory.buildSolver();


		long startTime = System.nanoTime();
		List<String> scores = new ArrayList<>();

		// affiche chaque amélioration de la solution
		solver.addEventListener(event -> {
			Timetable newBestSolution = event.getNewBestSolution();
			System.out.println("New best solution found with score: " + newBestSolution.getScore().toString() + " after " + (System.nanoTime() - startTime) + " ns");
			//System.out.println(newBestSolution);
			//analyser(newBestSolution, solverFactory);
			//scores.add(newBestSolution.getScore().toString());
			//System.out.println("All scores found: " + scores);

		});

		// résolution du problème
		Timetable solution = solver.solve(problem);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		System.out.println("Solution found in " + TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS) + " seconds");
		System.out.println(solution);

		// Zone d'analyse de la solution
		analyser(solution, solverFactory);

		scores.add(solution.getScore().toString());
		while (true) {
			solution.setScore(null);
			solution = solver.solve(solution);
			scores.add(solution.getScore().toString());
			System.out.println(solution);

			analyser(solution, solverFactory);

			System.out.println("All scores found: " + scores);
		}
	}

	private static void analyser(Timetable solution, SolverFactory<Timetable> solverFactory) {
		SolutionManager<Timetable, HardSoftScore> solutionManager = SolutionManager.create(solverFactory);
		ScoreAnalysis<HardSoftScore> scoreAnalysis = solutionManager.analyze(solution);

		List<ConstraintJustification> brokenCosntraints = new ArrayList<>();

		scoreAnalysis.constraintMap().forEach((x, constraintAnalysis) -> {
			if (constraintAnalysis.matches() == null) return;
			constraintAnalysis.matches().forEach(matchAnalysis -> {
				ConstraintJustification justification = matchAnalysis.justification();
				brokenCosntraints.add(justification);
				// brokenCosntraints.add("Constraint : " + matchAnalysis.constraintRef());
			});
		});

		System.out.println("Broken constraints:");
		brokenCosntraints.forEach(System.out::println);
	}
}
