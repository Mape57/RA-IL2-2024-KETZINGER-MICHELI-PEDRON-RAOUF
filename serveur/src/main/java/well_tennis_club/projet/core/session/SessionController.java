package well_tennis_club.projet.core.session;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import well_tennis_club.projet.core.player.dto.PlayerDto;
import well_tennis_club.projet.core.session.dto.NewSessionDto;
import well_tennis_club.projet.core.session.dto.SessionDto;
import well_tennis_club.projet.core.session.dto.SessionPlayerDto;
import well_tennis_club.projet.core.session.mapper.NewSessionMapperImpl;
import well_tennis_club.projet.core.session.mapper.SessionMapper;
import well_tennis_club.projet.exception.IdNotFoundException;
import well_tennis_club.projet.tool.ApiErrorResponse;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("sessions")
@Transactional
@CrossOrigin
public class SessionController {
	private final SessionService sessionService;
	private final NewSessionMapperImpl newSessionMapperImpl;

	@Autowired
	public SessionController(SessionService sessionService, NewSessionMapperImpl newSessionMapperImpl) {
		this.sessionService = sessionService;
		this.newSessionMapperImpl = newSessionMapperImpl;
	}

	// ========================= GET ========================= //
	@Operation(
			summary = "Retourne toutes les sessions",
			description = "Retourne toutes les sessions",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Recupération réussie",
					content = @Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = SessionDto.class))
					)
			)
	})
	@GetMapping
	public ResponseEntity<List<SessionDto>> getAllSessions() {
		List<SessionDto> sessions = SessionMapper.INSTANCE.mapToListDTO(sessionService.getAllSessions());
		return ResponseEntity.ok(sessions);
	}

	@Operation(
			summary = "Retourne la liste de tous les joueurs avec leurs sessions",
			description = "Retourne la liste de tous les joueurs avec leurs sessions",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Recupération réussie",
					content = @Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = SessionDto.class))
					)
			)
	})
	@GetMapping("/players")
	public ResponseEntity<List<SessionPlayerDto>> getAllSessionForPlayers(){

		List<SessionDto> allSessions = SessionMapper.INSTANCE.mapToListDTO(sessionService.getAllSessions());
		Map<PlayerDto, List<SessionDto>> map = new HashMap<>();

		for (SessionDto session : allSessions) {
			for (PlayerDto player : session.getPlayers()) {
				map.computeIfAbsent(player, k -> new ArrayList<>()).add(session);
			}
		}

		List<SessionPlayerDto> response = map.entrySet().stream()
				.map(entry -> new SessionPlayerDto(entry.getKey(), entry.getValue()))
				.toList();

		return ResponseEntity.ok(response);
	}

	// ========================= POST ========================= //
	@Operation(
			summary = "Crée une session",
			description = "Crée une session avec les informations fournies",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Création reussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = SessionDto.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Le DTO de la session est invalide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@PostMapping
	public ResponseEntity<SessionDto> createSession(@Valid @RequestBody NewSessionDto newSessionDto) {
		SessionEntity session = newSessionMapperImpl.mapToEntity(newSessionDto);
		session = sessionService.createSession(session);
		SessionDto sessionDto = SessionMapper.INSTANCE.mapToDTO(session);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(session.getId())
				.toUri();

		return ResponseEntity.created(location).body(sessionDto);
	}

	// ========================= PUT ========================= //
	@Operation(
			summary = "Met a jour la session",
			description = "Met a jour la session avec l'id spécifié",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Mise à jour reussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = SessionDto.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Le DTO ou l'id est mal formé",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "404",
					description = "Pas de terrain avec cet id",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@PutMapping("/{id}")
	public ResponseEntity<SessionDto> updateSession(@PathVariable UUID id, @Valid @RequestBody NewSessionDto sessionDto) {
		SessionEntity session = sessionService.getSessionById(id);
		if (session == null) {
			throw new IdNotFoundException("Pas de session avec cet id");
		} else {
			session = newSessionMapperImpl.mapToEntity(sessionDto);
			session.setId(id);
			session = sessionService.updateSession(session);
			return ResponseEntity.ok(SessionMapper.INSTANCE.mapToDTO(session));
		}
	}

	// ========================= DELETE ========================= //
	@Operation(
			summary = "Supprime une session",
			description = "Supprime une session avec l'id fourni",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "204",
					description = "Suppression réussie"
			),
			@ApiResponse(
					responseCode = "404",
					description = "Pas de session avec cet id",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
		int result = sessionService.deleteById(id);
		if (result == 0) {
			throw new IdNotFoundException("Pas de session avec cet id");
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
