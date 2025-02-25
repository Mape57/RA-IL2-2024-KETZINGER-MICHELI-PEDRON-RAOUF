package well_tennis_club.timefold.application;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import well_tennis_club.timefold.domain.Timetable;

public class TimetableSolver {
	public static Timetable run(Timetable problem) {
		// initialisation du solveur
		SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
		SolverFactory<Timetable> solverFactory = SolverFactory.create(solverConfig);
		Solver<Timetable> solver = solverFactory.buildSolver();

		// affiche chaque amélioration de la solution
		/*
		solver.addEventListener(event -> {
			Timetable newBestSolution = event.getNewBestSolution();
			System.out.println("New best solution found with score: " + newBestSolution.getScore().toString() + " after " + (System.nanoTime() - startTime) + " ns");
			//System.out.println(newBestSolution);
			//analyser(newBestSolution, solverFactory);
			//scores.add(newBestSolution.getScore().toString());
			//System.out.println("All scores found: " + scores);
		});
		*/

		return solver.solve(problem);
	}
}
