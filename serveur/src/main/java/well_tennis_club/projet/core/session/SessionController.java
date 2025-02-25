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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import well_tennis_club.projet.core.session.dto.CreateSessionDto;
import well_tennis_club.projet.core.session.dto.SessionDto;
import well_tennis_club.projet.core.session.mapper.CreateSessionMapper;
import well_tennis_club.projet.core.session.mapper.SessionMapper;
import well_tennis_club.projet.exception.IdNotFoundException;
import well_tennis_club.projet.tool.ApiErrorResponse;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("sessions")
@Transactional
@CrossOrigin
public class SessionController {
	private final SessionService sessionService;

	@Autowired
	public SessionController(SessionService sessionService) {
		this.sessionService = sessionService;
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
	// TODO revoir la creation d'une session
	public ResponseEntity<SessionDto> createSession(@Valid @RequestBody CreateSessionDto createSessionDto) {
		SessionEntity session = CreateSessionMapper.INSTANCE.mapToEntity(createSessionDto);
		session = sessionService.createSession(session);
		SessionDto sessionDto = SessionMapper.INSTANCE.mapToDTO(session);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(session.getId())
				.toUri();

		return ResponseEntity.created(location).body(sessionDto);
	}

	// ========================= PATCH ========================= //
	@Operation(summary = "Update session", description = "Update session with id",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Session was not update")
	})
	@PatchMapping("/{id}")
	public SessionDto updateSession(@PathVariable UUID id, @RequestBody SessionDto sessionDto) {
		SessionDto session = SessionMapper.INSTANCE.mapToDTO(sessionService.getSessionById(id));
		if (session == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Session not found"
			);
		} else {
			SessionDto modif = sessionDto;
			modif.setId(id);
			return SessionMapper.INSTANCE.mapToDTO(sessionService.updateSession(SessionMapper.INSTANCE.mapToEntity(modif)));
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
