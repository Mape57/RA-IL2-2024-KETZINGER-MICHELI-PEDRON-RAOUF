package well_tennis_club.projet.timetable;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import well_tennis_club.projet.court.CourtEntity;
import well_tennis_club.projet.court.CourtService;
import well_tennis_club.projet.player.PlayerEntity;
import well_tennis_club.projet.player.PlayerService;
import well_tennis_club.projet.session.SessionEntity;
import well_tennis_club.projet.sessionConstraint.SessionConstraintEntity;
import well_tennis_club.projet.trainer.TrainerEntity;
import well_tennis_club.projet.trainer.TrainerService;
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
	@PostMapping("/start")
	public ResponseEntity<String> startSolver() {
		if (problemId != null) {
			return ResponseEntity.status(409).body("Timetable solver was already started");
		}

		this.timetable = timetableFactory.createTimetable();

		this.problemId = UUID.randomUUID();
		solverManager.solveAndListen(this.problemId, this.timetable, (bestTimetable) -> {
			this.timetable = bestTimetable;
		});

		return ResponseEntity.ok("Solver started");
	}

	@Operation(summary = "Retrieve the timetable", description = "Retrieve the timetable")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "404", description = "Not found - Timetable was not found, try starting the solver first"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Timetable was not retrieved")
	})
	@GetMapping("")
	public ResponseEntity<Timetable> retrieveTimetable() {
		if (problemId == null) {
			return ResponseEntity.status(404).body(null);
		}

		return ResponseEntity.ok(this.timetable);
	}

	@GetMapping("/status")
	public ResponseEntity<String> status() {
		if (problemId == null) {
			return ResponseEntity.ok("Solver not started");
		}

		return ResponseEntity.ok("Solver started");
	}

	@PostMapping("/save")
	public ResponseEntity<String> saveTimetable() {
		if (problemId == null) {
			return ResponseEntity.status(404).body("Solver not started");
		}

		Map<Session, List<PlayerSessionLink>> session_withPSLs = new HashMap<>();
		for (PlayerSessionLink playerSessionLink : this.timetable.getPlayerSessionLinks()) {
			session_withPSLs.computeIfAbsent(playerSessionLink.getSession(), session -> new ArrayList<>())
					.add(playerSessionLink);
		}

		List<SessionEntity> sessionEntities = session_withPSLs.entrySet().stream()
				.map(entry -> {
					System.out.println(entry.getKey() + " -> " + entry.getValue());
					return from(entry.getKey(), entry.getValue());
				})
				.toList();

		timetableService.saveAllSession(sessionEntities);


		return ResponseEntity.ok("Timetable saved");
	}

	@PostMapping("/stop")
	public ResponseEntity<String> stopSolver() {
		if (problemId == null) {
			return ResponseEntity.status(404).body("Solver not started");
		}

		solverManager.terminateEarly(problemId);
		problemId = null;

		return ResponseEntity.ok("Solver stopped");
	}

	@GetMapping("/insertData")
	public ResponseEntity<String> insertData() {
		List<PlayerEntity> players = DataInsertion.players.real();
		List<TrainerEntity> trainers = DataInsertion.trainers.real();
		List<CourtEntity> courts = DataInsertion.tennisCourts.real;
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
}
