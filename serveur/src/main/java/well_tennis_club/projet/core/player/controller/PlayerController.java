package well_tennis_club.projet.core.player.controller;

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
import well_tennis_club.projet.core.DEPRECATED_participation.ParticipationService;
import well_tennis_club.projet.core.player.dto.CreatePlayerDto;
import well_tennis_club.projet.core.player.dto.PlayerDto;
import well_tennis_club.projet.core.player.dto.PutPlayerDto;
import well_tennis_club.projet.core.player.dto.ResponsePlayerDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.player.mapper.CreatePlayerMapper;
import well_tennis_club.projet.core.player.mapper.PutPlayerMapper;
import well_tennis_club.projet.core.player.mapper.ResponsePlayerMapperImpl;
import well_tennis_club.projet.core.player.service.PlayerService;
import well_tennis_club.projet.core.session.SessionService;
import well_tennis_club.projet.exception.IdNotFoundException;
import well_tennis_club.projet.tool.ApiErrorResponse;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("players")
@Transactional
@CrossOrigin
public class PlayerController {
	private final PlayerService playerService;
	private final ParticipationService participationService;
	private final SessionService sessionService;
	private final ResponsePlayerMapperImpl responsePlayerMapperImpl;

	@Autowired
	public PlayerController(PlayerService playerService, ParticipationService participationService, SessionService sessionService, ResponsePlayerMapperImpl responsePlayerMapperImpl) {
		this.playerService = playerService;
		this.participationService = participationService;
		this.sessionService = sessionService;
		this.responsePlayerMapperImpl = responsePlayerMapperImpl;
	}

	// ========================= GET ========================= //
	@Operation(
			summary = "Récupère tous les joueurs",
			description = "Retourne les joueurs avec le status spécifié",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Récupération réussie",
					content = @Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PlayerDto.class))
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Paramètre invalide ou manquant",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@GetMapping
	public ResponseEntity<List<ResponsePlayerDto>> getPlayerValidate(@RequestParam boolean validate) {
		List<PlayerEntity> playerEntities = playerService.getPlayerValidate(validate);
		List<ResponsePlayerDto> players = responsePlayerMapperImpl.mapToListDTO(playerEntities);
		return ResponseEntity.ok(players);
	}


	@Operation(
			summary = "Retourne le joueur avec cet id",
			description = "Retourne le joueur avec l'id spécifié si il existe",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Récupération réussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PlayerDto.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "L'id fourni n'est pas un UUID",
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
	@GetMapping("/{id}")
	public ResponseEntity<ResponsePlayerDto> getPlayer(@PathVariable UUID id) {
		PlayerEntity player = playerService.getPlayerById(id);
		if (player == null) {
			throw new IdNotFoundException("Pas de joueur avec cet id");
		} else {
			ResponsePlayerDto playerDto = responsePlayerMapperImpl.mapToDTO(player);
			return ResponseEntity.ok(playerDto);
		}
	}

	// ========================= POST ========================= //
	@Operation(
			summary = "Crée un joueur",
			description = "Crée un joueur avec nom, prenom, anniversaire, nombre de cours, niveau, email, status et disponibilités",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Creation reussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PlayerDto.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Le DTO envoye n'est pas valide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "409",
					description = "Conflit, données existante",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			),
	})
	@PostMapping
	public ResponseEntity<ResponsePlayerDto> createPlayer(@Valid @RequestBody CreatePlayerDto playerDto) {
		PlayerEntity player = CreatePlayerMapper.INSTANCE.mapToEntity(playerDto);
		player = playerService.createPlayer(player);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(player.getId())
				.toUri();

		return ResponseEntity.created(location).body(responsePlayerMapperImpl.mapToDTO(player));
	}

	// ========================= PUT ========================= //
	@Operation(
			summary = "Met à jour le joueur",
			description = "Met à jour le joueur avec cet id",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Mise à jour reussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PlayerDto.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Le DTO est mal formé",
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
			),
			@ApiResponse(
					responseCode = "409",
					description = "Conflit, données existante",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			),
	})
	@PutMapping("/{id}")
	public ResponseEntity<ResponsePlayerDto> updatePlayer(@PathVariable UUID id, @Valid @RequestBody PutPlayerDto playerDto) {
		PlayerEntity player = playerService.getPlayerById(id);
		if (player == null) {
			throw new IdNotFoundException("Pas de joueur avec cet id");
		} else {
			player = PutPlayerMapper.INSTANCE.mapToEntity(playerDto);
			player.setId(id);
			player = playerService.updatePlayer(player);
			return ResponseEntity.ok(responsePlayerMapperImpl.mapToDTO(player));
		}
	}

	// ========================= DELETE ========================= //
	@Operation(
			summary = "Supprime le joueur",
			description = "Supprime le joueur avec cet id",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "204",
					description = "Suppression réussie"
			),
			@ApiResponse(
					responseCode = "400",
					description = "L'id fourni n'est pas un UUID",
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
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePlayer(@PathVariable UUID id) {
		participationService.deleteByPlayerId(id);
		int result = playerService.deleteById(id);
		if (result == 0) {
			throw new IdNotFoundException("Pas de joueur avec cet id");
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@Operation(
			summary = "Supprime tout les joueurs",
			description = "Supprime les joueurs",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "204",
					description = "Suppression réussie"
			)
	})
	@DeleteMapping
	public ResponseEntity<Void> deleteAllPlayers() {
		sessionService.deleteAll();
		playerService.deleteAll();
		return ResponseEntity.noContent().build();
	}
}
