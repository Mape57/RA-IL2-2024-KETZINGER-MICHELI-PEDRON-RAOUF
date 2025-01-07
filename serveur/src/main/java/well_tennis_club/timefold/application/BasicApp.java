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

import static well_tennis_club.timefold.application.TimetableController.getProblem;

public class BasicApp {
	public static void main(String[] args) {
		SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
		SolverFactory<Timetable> solverFactory = SolverFactory.create(solverConfig);
		Timetable problem = getProblem();
		Solver<Timetable> solver = solverFactory.buildSolver();
		Timetable solution = solver.solve(problem);

		System.out.println("Solution:");
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

}
