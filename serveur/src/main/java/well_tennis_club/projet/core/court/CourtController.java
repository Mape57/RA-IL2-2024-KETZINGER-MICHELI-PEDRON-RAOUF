package well_tennis_club.projet.core.court;

import io.swagger.v3.oas.annotations.Operation;
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
import well_tennis_club.projet.exception.IdNotFoundException;
import well_tennis_club.projet.tool.ApiErrorResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("courts")
@Transactional
@CrossOrigin
public class CourtController {
	private final CourtService courtService;

	@Autowired
	public CourtController(CourtService courtService) {
		this.courtService = courtService;
	}

	// ========================= GET ========================= //
	@Operation(
			summary = "Réupère les terrains de tennis",
			description = "Retourne l'ensemble des terrains de tennis avec leurs horaires",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Récupération réussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = CourtDto.class)
					)
			)
	})
	@GetMapping
	public ResponseEntity<List<CourtDto>> getAllCourts() {
		List<CourtEntity> courts = courtService.getAllCourts();
		return ResponseEntity.ok(CourtMapper.INSTANCE.mapToListDTO(courts));
	}


	// ========================= PATCH ========================= //
	@Operation(
			summary = "Met a jour un terrain de tennis",
			description = "Met a jour le terrain de tennis pour l'id spécifié",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Mise a jour reussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = CourtDto.class)
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
					description = "Pas de joueur avec cet id",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@PatchMapping("/{id}")
	public ResponseEntity<CourtDto> updateCourt(@PathVariable UUID id, @Valid @RequestBody CourtDto courtDto) {
		CourtEntity court = courtService.getCourtById(id);
		if (court == null) {
			throw new IdNotFoundException("Pas de terrain avec cet id");
		} else {
			courtDto.setId(id);
			court = courtService.updateCourt(CourtMapper.INSTANCE.mapToEntity(courtDto));
			return ResponseEntity.ok(CourtMapper.INSTANCE.mapToDTO(court));
		}
	}
}
