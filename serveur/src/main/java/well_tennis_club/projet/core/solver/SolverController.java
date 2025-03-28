package well_tennis_club.projet.core.solver;

import ai.timefold.solver.core.api.score.analysis.ScoreAnalysis;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.SolverManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import well_tennis_club.projet.core.session.SessionService;
import well_tennis_club.projet.core.session_constraint.SessionConstraintEntity;
import well_tennis_club.projet.core.solver.dto.SessionJustificationsDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.projet.core.trainer.service.TrainerService;
import well_tennis_club.projet.exception.SolverRequestOrderException;
import well_tennis_club.projet.tool.ApiErrorResponse;
import well_tennis_club.projet.tool.DataInsertion;
import well_tennis_club.projet.tool.TimetableFactory;
import well_tennis_club.timefold.domain.*;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;
import well_tennis_club.timefold.solver.justifications.groupe.SessionsJustification;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

	private UUID problemId;
	private Timetable timetable;
	private SessionService sessionService;
	private static List<UUID> updatedSessions = new ArrayList<>();

	@Autowired
	public SolverController(TimetableFactory timetableFactory, TimetableService timetableService, PlayerService playerService, CourtService courtService, TrainerService trainerService, SolverManager<Timetable, UUID> solverManager, SolutionManager<Timetable, HardSoftScore> solutionManager, SessionService sessionService) {
		this.timetableFactory = timetableFactory;
		this.timetableService = timetableService;
		this.playerService = playerService;
		this.courtService = courtService;
		this.trainerService = trainerService;
		this.solverManager = solverManager;
		this.solutionManager = solutionManager;
		this.sessionService = sessionService;
	}

	@Operation(
			summary = "Lance le solveur",
			description = "Lance le solveur pour résoudre le problème de planification des sessions de tennis",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Solveur lancé"
			),
			@ApiResponse(
					responseCode = "409",
					description = "Le solveur est déjà en cours d'exécution",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@PostMapping
	public ResponseEntity<String> startSolver() {
		if (problemId != null) {
			throw new SolverRequestOrderException("Le solveur est déjà en cours d'exécution");
		}

		updatedSessions.clear();
		this.timetable = timetableFactory.createTimetable();

		this.problemId = UUID.randomUUID();

		solverManager.solveBuilder().withProblemId(problemId).withProblem(timetable).withBestSolutionConsumer(bestSolution -> {
			System.out.println("New best solution found: " + bestSolution.getScore());
			this.timetable = bestSolution;
		}).withFinalBestSolutionConsumer(finalBestSolution -> {
			System.out.println("Final best solution found: " + finalBestSolution.getScore());
			this.timetable = finalBestSolution;
			this.problemId = null;
			saveTimetable();
		}).run();

		return ResponseEntity.ok("Solveur lancé");
	}

	@Operation(
			summary = "Récupère le statut du solveur",
			description = "Récupère le statut du solveur (non lancé, terminé, en cours)",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Statut du solveur récupéré",
					content = @Content(
							mediaType = "text/plain",
							schema = @Schema(type = "string", example = "Le solveur n'est pas lancé")
					)
			)
	})
	@GetMapping
	public ResponseEntity<String> status() {
		if (problemId == null && timetable == null) {
			return ResponseEntity.ok("Le solveur n'est pas lancé");
		} else if (problemId == null) {
			return ResponseEntity.ok("Le solveur est terminé, meilleur score: " + timetable.getScore());
		} else {
			return ResponseEntity.ok("Le solveur est lancé, meilleur score actuel: " + timetable.getScore());
		}
	}

	@GetMapping("/rmpk")
	public ResponseEntity<String> getTimetable() {
		if (problemId == null && timetable == null) {
			throw new SolverRequestOrderException("Le solveur n'est pas lancé.");
		} else if (problemId != null) {
			throw new SolverRequestOrderException("Le solveur est toujours en cours.");
		} else {
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
				objectOutputStream.writeObject(timetable);
				objectOutputStream.close();
				String compressedTimetable = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
				return ResponseEntity.ok(compressedTimetable);
			} catch (IOException e) {
				System.out.println("Error compressing timetable: " + e.getMessage());
				return ResponseEntity.status(500).body("Error compressing timetable");
			}
		}
	}

	@PostMapping("/rmpk")
	public ResponseEntity<String> setTimetable(@RequestBody String compressedTimetable) {
		if (problemId == null && this.timetable == null) {
			try {
				System.out.println("Setting timetable " + compressedTimetable);
				byte[] compressedBytes = Base64.getDecoder().decode(compressedTimetable);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedBytes);
				GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
				ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);
				this.timetable = (Timetable) objectInputStream.readObject();
				objectInputStream.close();
				sessionService.deleteAll();
				saveTimetable();

				return ResponseEntity.ok("Timetable set");
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error decompressing timetable: " + e.getMessage());
				return ResponseEntity.status(500).body("Error decompressing timetable");
			}
		} else if (problemId != null) {
			return ResponseEntity.ok("Solver is running");
		} else {
			return ResponseEntity.ok("Timetable already set");
		}
	}

	@Operation(
			summary = "Récupère les justifications",
			description = "Récupère les justifications des contraintes non respectées par le solveur",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Justifications récupérées",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = SessionJustificationsDto.class)
					)
			),
			@ApiResponse(
					responseCode = "409",
					description = "Le solveur n'est pas lancé",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@GetMapping("/justifications")
	public ResponseEntity<List<SessionJustificationsDto>> justifications() {
		if (problemId == null && timetable == null) {
			throw new SolverRequestOrderException("Le solveur n'est pas lancé.");
		} else if (problemId != null) {
			throw new SolverRequestOrderException("Le solveur est toujours en cours.");
		}

		Map<Session, SessionJustificationsDto> sessionJustificationsDtos = new HashMap<>();

		ScoreAnalysis<HardSoftScore> scoreAnalysis = solutionManager.analyze(timetable);

		scoreAnalysis.constraintMap().forEach((x, constraintAnalysis) -> {
			if (constraintAnalysis.matches() == null) return;

			constraintAnalysis.matches().forEach(matchAnalysis -> {
				ConstraintJustification justification = matchAnalysis.justification();

				switch (justification) {
					case SessionJustification sessionJustification ->
							addJustification(sessionJustificationsDtos, sessionJustification.getSession(), sessionJustification.getDescription(), sessionJustification.getScore());
					case SessionsJustification sessionsJustification ->
							sessionsJustification.getSessions().forEach(session -> addJustification(sessionJustificationsDtos, session, sessionsJustification.getDescription(), sessionsJustification.getScore()));
					default -> {
						System.out.println("Type de justification non pris en charge : " + justification);
					}
				}
			});
		});

		return ResponseEntity.ok(sessionJustificationsDtos.values().stream().toList());
	}

	public static void modifiedSession(SessionEntity session) {
		updatedSessions.add(session.getId());
	}

	private void addJustification(Map<Session, SessionJustificationsDto> sessionJustificationsDtos, Session session, String description, HardSoftScore score) {
		if (updatedSessions.contains(session.getId())) return;

		SessionJustificationsDto current_sjd = sessionJustificationsDtos.computeIfAbsent(session, s -> {
			List<PlayerSessionLink> psls = this.timetable.getPlayerSessionLinks().stream().filter(psl -> psl.getSession().equals(s)).toList();
			SessionEntity sessionEntity = from(s, psls);

			return new SessionJustificationsDto(sessionEntity);
		});
		current_sjd.addJustification(description, score.toString());
	}

	public void saveTimetable() {
		if (timetable == null) {
			return;
		}

		Map<Session, List<PlayerSessionLink>> session_withPSLs = new HashMap<>();
		for (PlayerSessionLink playerSessionLink : this.timetable.getPlayerSessionLinks()) {
			session_withPSLs.computeIfAbsent(playerSessionLink.getSession(), session -> new ArrayList<>()).add(playerSessionLink);
		}

		List<SessionEntity> sessionEntities = session_withPSLs.entrySet().stream().map(entry -> from(entry.getKey(), entry.getValue())).toList();

		timetableService.saveAllSession(sessionEntities);
	}

	@GetMapping("/insertData")
	public ResponseEntity<String> insertData() {
		List<PlayerEntity> players = DataInsertion.players.small;
		List<TrainerEntity> trainers = DataInsertion.trainers.small;
		List<CourtEntity> courts = DataInsertion.tennisCourts.small;
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
