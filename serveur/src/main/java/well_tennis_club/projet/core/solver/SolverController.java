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
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.domain.Timetable;
import well_tennis_club.timefold.domain.Trainer;
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

	private static final int NB_SOLVER = 10;
	private UUID[] problemIds;
	private Timetable[] timetables;
	private Timetable bestTimetable;
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
		if (problemIds != null) {
			throw new SolverRequestOrderException("Le solveur est déjà en cours d'exécution");
		}

		updatedSessions.clear();
		this.timetables = new Timetable[NB_SOLVER];
		this.problemIds = new UUID[NB_SOLVER];

		for (int i = 0; i < NB_SOLVER; i++) {
			try {
				this.timetables[i] = timetableFactory.createTimetable();
			} catch (SolverRequestOrderException e) {
				this.timetables = null;
				this.problemIds = null;
				this.bestTimetable = null;
				throw new SolverRequestOrderException(e.getMessage());
			}
			this.problemIds[i] = UUID.randomUUID();

			int finalI = i;
			solverManager.solveBuilder().withProblemId(this.problemIds[i]).withProblem(this.timetables[i]).withBestSolutionConsumer(bestSolution -> {
				System.out.println(finalI + " - New best solution found: " + bestSolution.getScore());
				this.timetables[finalI] = bestSolution;
			}).withFinalBestSolutionConsumer(finalBestSolution -> {
				System.out.println(finalI + " - Final best solution found: " + finalBestSolution.getScore());
				this.timetables[finalI] = finalBestSolution;
				this.problemIds[finalI] = null;

				if (Arrays.stream(problemIds).allMatch(Objects::isNull)) {
					this.problemIds = null;
					bestTimetable();
					saveTimetable();
				}
			}).run();
		}

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
		if (problemIds == null && timetables == null && bestTimetable == null) {
			return ResponseEntity.ok("STOPPED");
		} else if (problemIds == null) {
			return ResponseEntity.ok("OVER");
		} else {
			return ResponseEntity.ok("RUNNING");
		}
	}

	@GetMapping("/ki")
	public ResponseEntity<String> blankIt() {
		stopSolver();
		timetables = null;
		this.bestTimetable = null;
		return ResponseEntity.ok("STOPPED");
	}

	@GetMapping("/rmpk")
	public ResponseEntity<String> getTimetable() {
		if (problemIds == null && timetables == null && bestTimetable == null) {
			throw new SolverRequestOrderException("Le solveur n'est pas lancé.");
		} else if (problemIds != null) {
			throw new SolverRequestOrderException("Le solveur est toujours en cours.");
		} else {
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
				objectOutputStream.writeObject(bestTimetable);
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
		if (problemIds != null) {
			throw new SolverRequestOrderException("Le solveur est toujours en cours.");
		}
		try {
			System.out.println("Setting timetable " + compressedTimetable);
			byte[] compressedBytes = Base64.getDecoder().decode(compressedTimetable);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedBytes);
			GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
			ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);
			this.bestTimetable = (Timetable) objectInputStream.readObject();
			objectInputStream.close();
			sessionService.deleteAll();
			updatedSessions.clear();
			saveTimetable();

			return ResponseEntity.ok("Timetable set");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error decompressing timetable: " + e.getMessage());
			return ResponseEntity.status(500).body("Error decompressing timetable");
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
		if (problemIds == null && timetables == null && bestTimetable == null) {
			throw new SolverRequestOrderException("Le solveur n'est pas lancé.");
		} else if (problemIds != null) {
			throw new SolverRequestOrderException("Le solveur est toujours en cours.");
		}

		Map<Session, SessionJustificationsDto> sessionJustificationsDtos = new HashMap<>();

		ScoreAnalysis<HardSoftScore> scoreAnalysis = solutionManager.analyze(bestTimetable);

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
			List<PlayerSessionLink> psls = this.bestTimetable.getPlayerSessionLinks().stream().filter(psl -> psl.getSession().equals(s)).toList();
			SessionEntity sessionEntity = from(s, psls);

			return new SessionJustificationsDto(sessionEntity);
		});
		current_sjd.addJustification(description, score.toString());
	}

	public void saveTimetable() {
		if (this.bestTimetable == null) {
			return;
		}

		Map<Session, List<PlayerSessionLink>> session_withPSLs = new HashMap<>();
		for (PlayerSessionLink playerSessionLink : this.bestTimetable.getPlayerSessionLinks()) {
			session_withPSLs.computeIfAbsent(playerSessionLink.getSession(), session -> new ArrayList<>()).add(playerSessionLink);
		}

		List<SessionEntity> sessionEntities = session_withPSLs.entrySet().stream().map(entry -> from(entry.getKey(), entry.getValue())).toList();

		timetableService.saveAllSession(sessionEntities);
	}

	private void bestTimetable() {
		System.out.println("Best timetable found: " + Arrays.stream(timetables).map(t -> t.getScore().toString()).toList());
		this.bestTimetable = Arrays.stream(timetables).max(Comparator.comparing(t -> t.getScore().softScore() + t.getScore().hardScore() * 10)).get();
		System.out.println("Keeping: " + this.bestTimetable.getScore());
	}

	@Operation(
			summary = "Insère les données",
			description = "Insère les données de test dans la base de données",
			security = @SecurityRequirement(name = "bearerAuth")
	)
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

	// ========================= DEPRECATED ========================= //

	@Deprecated(forRemoval = true)
	@GetMapping("/stop")
	public ResponseEntity<String> stopSolver() {
		if (problemIds == null) {
			return ResponseEntity.status(404).body("Solver not started");
		}

		for (int i = 0; i < NB_SOLVER; i++) {
			if (problemIds[i] != null) {
				solverManager.terminateEarly(problemIds[i]);
			}
		}
		problemIds = null;

		return ResponseEntity.ok("Solver stopped");
	}
}
