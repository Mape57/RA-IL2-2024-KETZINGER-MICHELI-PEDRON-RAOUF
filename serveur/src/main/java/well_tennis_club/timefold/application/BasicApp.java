package well_tennis_club.timefold.application;

import ai.timefold.solver.core.api.score.analysis.ScoreAnalysis;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import well_tennis_club.timefold.domain.*;
import well_tennis_club.timefold.tools.SampleData;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class BasicApp {
	public static void main(String[] args) {
		// initialisation du solveur
		SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
		SolverFactory<Timetable> solverFactory = SolverFactory.create(solverConfig);
		Solver<Timetable> solver = solverFactory.buildSolver();

		long startTime = System.nanoTime();

		// affiche chaque amélioration de la solution
		solver.addEventListener(event -> {
			Timetable newBestSolution = event.getNewBestSolution();
			System.out.println("New best solution found with score: " + newBestSolution.getScore().toString() + " after " + (System.nanoTime() - startTime) + " ns");
			//System.out.println(newBestSolution);
			//analyser(newBestSolution, solverFactory);
			//scores.add(newBestSolution.getScore().toString());
			//System.out.println("All scores found: " + scores);

		});

		Timetable problem = new Timetable(SampleData.players.real(), SampleData.trainers.real(), SampleData.tennisCourts.real);

		Timetable solution = solver.solve(problem);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		System.out.println("Solution found in " + TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS) + " seconds");
		System.out.println(solution);
		analyser(solution, solverFactory);
	}

	public static void main2(String[] args) {
		// initialisation du solveur
		SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
		SolverFactory<Timetable> solverFactory = SolverFactory.create(solverConfig);
		Solver<Timetable> solver = solverFactory.buildSolver();


		long startTime = System.nanoTime();

		// affiche chaque amélioration de la solution
		solver.addEventListener(event -> {
			Timetable newBestSolution = event.getNewBestSolution();
			System.out.println("New best solution found with score: " + newBestSolution.getScore().toString() + " after " + (System.nanoTime() - startTime) + " ns");
			//System.out.println(newBestSolution);
		});

		List<Trainer> trainers = SampleData.trainers.real();
		List<TennisCourt> tennisCourts = SampleData.tennisCourts.real;
		// tout les problemes
		Timetable problem_3_4 = new Timetable(SampleData.players.real_3_4, trainers, tennisCourts);
		Timetable problem_5_7 = new Timetable(SampleData.players.real_5_7, trainers, tennisCourts);
		Timetable problem_8_18 = new Timetable(SampleData.players.real_8_18, trainers, tennisCourts);
		Timetable problem_adult = new Timetable(SampleData.players.real_adult, trainers, tennisCourts);

		// résolution des problemes
		Timetable solution_3_4 = solver.solve(problem_3_4);
		Timetable solution_5_7 = solver.solve(problem_5_7);
		Timetable solution_8_18 = solver.solve(problem_8_18);
		Timetable solution_adult = solver.solve(problem_adult);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		System.out.println("Solutions found in " + TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS) + " seconds");
		System.out.println("=============================================================== 3-4 ===============================================================");
		System.out.println(solution_3_4);
		System.out.println("=============================================================== 5-7 ===============================================================");
		System.out.println(solution_5_7);
		System.out.println("=============================================================== 8-18 ===============================================================");
		System.out.println(solution_8_18);
		System.out.println("=============================================================== adult ===============================================================");
		System.out.println(solution_adult);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Press any key to combine all solutions and solve them");
		scanner.nextLine();

		Timetable problem = new Timetable(SampleData.players.real(), trainers, tennisCourts);

		// combinaison des solutions
		for (Session session : problem.getSessions()) {
			Trainer trainer = findTrainer(solution_3_4, session);
			if (trainer == null) trainer = findTrainer(solution_5_7, session);
			if (trainer == null) trainer = findTrainer(solution_8_18, session);
			if (trainer == null) trainer = findTrainer(solution_adult, session);
			session.setTrainer(trainer);
		}

		Map<Player, Integer> playerSessionLinkCount = new HashMap<>();
		for (PlayerSessionLink playerSessionLink : problem.getPlayerSessionLinks()) {
			int current = playerSessionLinkCount.getOrDefault(playerSessionLink.getPlayer(), 0);
			playerSessionLinkCount.put(playerSessionLink.getPlayer(), current + 1);

			List<PlayerSessionLink> playerSessionLinks = findPlayerSessionLinks(solution_3_4, playerSessionLink);
			int psls_size = playerSessionLinks.size();
			if (playerSessionLinks.isEmpty() || psls_size < current) {
				playerSessionLinks = findPlayerSessionLinks(solution_5_7, playerSessionLink);
				psls_size = playerSessionLinks.size();
			}
			if (playerSessionLinks.isEmpty() || psls_size < current) {
				playerSessionLinks = findPlayerSessionLinks(solution_8_18, playerSessionLink);
				psls_size = playerSessionLinks.size();
			}
			if (playerSessionLinks.isEmpty() || psls_size < current) {
				playerSessionLinks = findPlayerSessionLinks(solution_adult, playerSessionLink);
				psls_size = playerSessionLinks.size();
			}

			if (playerSessionLinks.isEmpty()) continue;
			playerSessionLink.setSession(playerSessionLinks.get(current).getSession());
		}

		System.out.println(problem);

		scanner.nextLine();
		Timetable solution = solver.solve(problem);
		System.out.println(solution);
		solution = solver.solve(solution);
		System.out.println(solution);
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

	private static Trainer findTrainer(Timetable timetable, Session session) {
		return timetable.getSessions().stream()
				.filter(s -> Objects.equals(s.getTennisCourt(), session.getTennisCourt()) && Objects.equals(s.getDay().getValue(), session.getDay().getValue()) && Objects.equals(s.getStartTime(), session.getStartTime()))
				.findFirst()
				.map(Session::getTrainer)
				.orElse(null);
	}

	private static List<PlayerSessionLink> findPlayerSessionLinks(Timetable timetable, PlayerSessionLink playerSessionLink) {
		return timetable.getPlayerSessionLinks().stream()
				.filter(psl -> Objects.equals(psl.getPlayer(), playerSessionLink.getPlayer()))
				.toList();
	}
}
