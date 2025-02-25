package well_tennis_club.projet.core.session_constraint;

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
import well_tennis_club.projet.core.session_constraint.dto.CreateSessionConstraintDto;
import well_tennis_club.projet.core.session_constraint.dto.SessionConstraintDto;
import well_tennis_club.projet.core.session_constraint.mapper.CreateSessionConstraintMapper;
import well_tennis_club.projet.core.session_constraint.mapper.SessionConstraintMapper;
import well_tennis_club.projet.exception.IdNotFoundException;
import well_tennis_club.projet.tool.ApiErrorResponse;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("constraints")
@Transactional
@CrossOrigin
public class SessionConstraintController {
	private final SessionConstraintService sessionConstraintService;

	@Autowired
	public SessionConstraintController(SessionConstraintService sessionConstraintService) {
		this.sessionConstraintService = sessionConstraintService;
	}

	// ========================= GET ========================= //
	@Operation(
			summary = "Récupère les contraintes de session",
			description = "Retourne l'ensemble des contraintes de session",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Récupération réussie",
					content = @Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = SessionConstraintDto.class))
					)
			)
	})
	@GetMapping
	public ResponseEntity<List<SessionConstraintDto>> getAllCourts() {
		List<SessionConstraintEntity> courts = sessionConstraintService.getAllConstraints();
		return ResponseEntity.ok(SessionConstraintMapper.INSTANCE.mapToListDTO(courts));
	}

	// ========================= POST ========================= //
	@Operation(
			summary = "Crée un joueur",
			description = "Crée un joueur avec nom, prénom, anniversaire, nombre de cours, niveau, email, status et disponibilités",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Création réussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = CreateSessionConstraintDto.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Le DTO envoyé n'est pas valide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			),
	})
	@PostMapping
	public ResponseEntity<SessionConstraintDto> createPlayer(@Valid @RequestBody CreateSessionConstraintDto createSessionConstraintDto) {
		SessionConstraintEntity constraint = CreateSessionConstraintMapper.INSTANCE.mapToEntity(createSessionConstraintDto);
		constraint = sessionConstraintService.createConstraint(constraint);
		SessionConstraintDto constraintDto = SessionConstraintMapper.INSTANCE.mapToDTO(constraint);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(constraint.getId())
				.toUri();

		return ResponseEntity.created(location).body(constraintDto);
	}

	// ========================= PATCH ========================= //
	@Operation(summary = "Update constraint", description = "Update constraint with id",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully patched"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Constraint was not update")
	})
	@PatchMapping("/{id}")
	public SessionConstraintDto updateConstraint(@PathVariable UUID id, @RequestBody SessionConstraintDto sessionconstraintDto) {
		SessionConstraintDto constraint = SessionConstraintMapper.INSTANCE.mapToDTO(sessionConstraintService.getConstraintById(id));
		if (constraint == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Constraint not found"
			);
		} else {
			SessionConstraintDto modif = sessionconstraintDto;
			modif.setId(id);
			return SessionConstraintMapper.INSTANCE.mapToDTO(sessionConstraintService.updateConstraint(SessionConstraintMapper.INSTANCE.mapToEntity(modif)));
		}
	}

	// ========================= DELETE ========================= //
	@Operation(
			summary = "Supprime la contrainte",
			description = "Supprime la contrainte de session avec cet id",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "204",
					description = "Suppression reussie"
			),
			@ApiResponse(
					responseCode = "404",
					description = "Pas de contrainte avec cet id",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteConstraint(@PathVariable UUID id) {
		int result = sessionConstraintService.deleteById(id);
		if (result == 0) {
			throw new IdNotFoundException("Pas de contrainte avec cet id");
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
