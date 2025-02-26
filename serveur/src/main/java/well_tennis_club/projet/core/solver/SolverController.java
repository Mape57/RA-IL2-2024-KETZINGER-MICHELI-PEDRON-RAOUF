package well_tennis_club.projet.core.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.SolverManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import well_tennis_club.projet.core.court.CourtEntity;
import well_tennis_club.projet.core.court.CourtService;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.player.service.PlayerService;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session_constraint.SessionConstraintEntity;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.projet.core.trainer.service.TrainerService;
import well_tennis_club.projet.tool.DataInsertion;
import well_tennis_club.projet.tool.TimetableFactory;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.domain.Timetable;
import well_tennis_club.timefold.domain.Trainer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@RestController
@RequestMapping("solver")
@CrossOrigin
public class SolverController {
	private TimetableFactory timetableFactory;
	private TimetableService timetableService;
	private PlayerService playerService;
	private CourtService courtService;
	private TrainerService trainerService;

	private SolverManager<Timetable, UUID> solverManager;
	private SolutionManager<Timetable, HardSoftScore> solutionManager;

	@Autowired
	public SolverController(TimetableFactory timetableFactory, TimetableService timetableService,
							PlayerService playerService, CourtService courtService, TrainerService trainerService,
							SolverManager<Timetable, UUID> solverManager, SolutionManager<Timetable, HardSoftScore> solutionManager) {
		this.timetableFactory = timetableFactory;
		this.timetableService = timetableService;
		this.playerService = playerService;
		this.courtService = courtService;
		this.trainerService = trainerService;
		this.solverManager = solverManager;
		this.solutionManager = solutionManager;
	}

	private UUID problemId;
	private Timetable timetable;

	@Operation(summary = "Start the timetable solver", description = "Start the timetable solver")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully started"),
			@ApiResponse(responseCode = "409", description = "Conflict - Timetable solver was already started"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Timetable solver was not started")
	})
	@PostMapping
	public ResponseEntity<String> startSolver() {
		if (problemId != null) {
			return ResponseEntity.status(409).body("Timetable solver was already started");
		}

		this.timetable = timetableFactory.createTimetable();

		this.problemId = UUID.randomUUID();

		solverManager.solveBuilder()
				.withProblemId(problemId)
				.withProblem(timetable)
				.withBestSolutionConsumer(bestSolution -> {
					System.out.println("New best solution found: " + bestSolution.getScore());
					this.timetable = bestSolution;
				})
				.withFinalBestSolutionConsumer(finalBestSolution -> {
					System.out.println("Final best solution found: " + finalBestSolution.getScore());
					this.timetable = finalBestSolution;
					this.problemId = null;
					saveTimetable();
				})
				.run();

		return ResponseEntity.ok("Solver started");
	}

	@GetMapping
	public ResponseEntity<String> status() {
		if (problemId == null && timetable == null) {
			return ResponseEntity.ok("Solver not started");
		} else if (problemId == null) {
			return ResponseEntity.ok("Solver finished, final score: " + timetable.getScore());
		} else {
			return ResponseEntity.ok("Solver running, current score: " + timetable.getScore());
		}
	}

	public void saveTimetable() {
		if (timetable == null) {
			return;
		}

		Map<Session, List<PlayerSessionLink>> session_withPSLs = new HashMap<>();
		for (PlayerSessionLink playerSessionLink : this.timetable.getPlayerSessionLinks()) {
			session_withPSLs.computeIfAbsent(playerSessionLink.getSession(), session -> new ArrayList<>())
					.add(playerSessionLink);
		}

		List<SessionEntity> sessionEntities = session_withPSLs.entrySet().stream()
				.map(entry -> from(entry.getKey(), entry.getValue()))
				.toList();

		timetableService.saveAllSession(sessionEntities);
	}

	@GetMapping("/insertData")
	public ResponseEntity<String> insertData() {
		List<PlayerEntity> players = DataInsertion.players.real_3_4;
		List<TrainerEntity> trainers = DataInsertion.trainers.simple;
		List<CourtEntity> courts = DataInsertion.tennisCourts.simple;
		List<SessionConstraintEntity> sessionConstraints = DataInsertion.sessionConstraints.real;

		timetableService.saveAllPlayer(players);
		timetableService.saveAllTrainer(trainers);
		timetableService.saveAllCourt(courts);
		timetableService.saveAllSessionConstraint(sessionConstraints);

		return ResponseEntity.ok("Data inserted");
	}

	private SessionEntity from(Session session, List<PlayerSessionLink> PSLs) {
		SessionEntity sessionEntity = new SessionEntity();
		sessionEntity.setId(session.getId());

		CourtEntity courtEntity = courtService.getCourtById(session.getTennisCourt());
		sessionEntity.setIdCourt(courtEntity);

		Trainer trainer = session.getTrainer();
		if (trainer != null) {
			TrainerEntity trainerEntity = trainerService.getTrainerById(trainer.getId());
			sessionEntity.setIdTrainer(trainerEntity);
		}

		if (PSLs.isEmpty()) return null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime start = session.getStartTime();
		int duration = PSLs.getFirst().getPlayer().getSessionConstraint().duration();

		sessionEntity.setStart(start.format(formatter));
		sessionEntity.setStop(start.plusMinutes(duration).format(formatter));
		sessionEntity.setDayWeek(session.getDay().getValue());

		List<PlayerEntity> playerEntities = new ArrayList<>();
		for (PlayerSessionLink PSL : PSLs) {
			PlayerEntity playerEntity = playerService.getPlayerById(PSL.getPlayer().getId());
			boolean sessionFound = false;
			for (PlayerEntity x_playerEntity : playerEntities) {
				if (x_playerEntity.getId().equals(playerEntity.getId())) {
					sessionFound = true;
					break;
				}
			}
			if (!sessionFound) playerEntities.add(playerEntity);
		}
		sessionEntity.setPlayers(playerEntities);

		return sessionEntity;
	}

	// ========================= DEPRECATED ========================= //

	@Deprecated(forRemoval = true)
	@GetMapping("/stop")
	public ResponseEntity<String> stopSolver() {
		if (problemId == null) {
			return ResponseEntity.status(404).body("Solver not started");
		}

		solverManager.terminateEarly(problemId);
		problemId = null;

		return ResponseEntity.ok("Solver stopped");
	}
}
