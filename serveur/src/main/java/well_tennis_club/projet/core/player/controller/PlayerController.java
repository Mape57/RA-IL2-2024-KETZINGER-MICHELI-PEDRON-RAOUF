package well_tennis_club.projet.core.player.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import well_tennis_club.projet.core.player.dto.CreatePlayerDto;
import well_tennis_club.projet.core.player.dto.PlayerDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.player.mapper.CreatePlayerMapper;
import well_tennis_club.projet.core.player.mapper.PlayerMapper;
import well_tennis_club.projet.core.player.service.PlayerService;
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

	@Autowired
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	// ========================= GET ========================= //
	@Operation(
			summary = "Recupere les joueurs ayant ce status",
			description = "Retourne les joueurs etant valide par l'admin ou non"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Recuperation reussie",
					content = @Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PlayerDto.class))
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Parametre invalide ou manquant",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@GetMapping
	// TODO retourner une version minimum du joueur ?? (validation constraint dans le front va marcher ?)
	public ResponseEntity<List<PlayerDto>> getPlayerValidate(@RequestParam boolean validate) {
		List<PlayerEntity> playerEntities = playerService.getPlayerValidate(validate);
		List<PlayerDto> players = PlayerMapper.INSTANCE.mapToListDTO(playerEntities);
		return ResponseEntity.ok(players);
	}


	@Operation(
			summary = "Retourne le joueur avec cet id",
			description = "Retourne le joueur avec l'id specifie s'il existe"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Recuperation reussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PlayerDto.class)
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
	public ResponseEntity<PlayerDto> getPlayer(@PathVariable UUID id) {
		PlayerDto player = PlayerMapper.INSTANCE.mapToDTO(playerService.getPlayerById(id));
		if (player == null) {
			throw new IdNotFoundException("Pas de joueur avec cet id");
		} else {
			return ResponseEntity.ok(player);
		}
	}

	// ========================= POST ========================= //
	@Operation(
			summary = "Cree un joueur",
			description = "Cree un joueur avec nom, prenom, anniversaire, nombre de cours, niveau, email, status et disponibilites"
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
			// TODO @ApiResponse(responseCode = "409", description = "The email is already used")
	})
	@PostMapping
	public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody CreatePlayerDto playerDto) {
		PlayerEntity player = CreatePlayerMapper.INSTANCE.mapToEntity(playerDto);
		player = playerService.createPlayer(player);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(player.getId())
				.toUri();

		return ResponseEntity.created(location).body(PlayerMapper.INSTANCE.mapToDTO(player));
	}

	// ========================= PATCH ========================= //
	@Operation(
			summary = "Met a jour le joueur",
			description = "Met a jour le joueur avec cet id"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Mise a jour reussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PlayerDto.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Le DTO est mal forme",
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
	public ResponseEntity<PlayerDto> updatePlayer(@PathVariable UUID id, @RequestBody PlayerDto playerDto) {
		PlayerEntity player = playerService.getPlayerById(id);
		if (player == null) {
			throw new IdNotFoundException("Pas de joueur avec cet id");
		} else {
			playerDto.setId(id);
			player = playerService.updatePlayer(PlayerMapper.INSTANCE.mapToEntity(playerDto));
			return ResponseEntity.ok(PlayerMapper.INSTANCE.mapToDTO(player));
		}
	}

	// ========================= DELETE ========================= //
	@Operation(
			summary = "Supprime le joueur",
			description = "Supprime le joueur avec cet id"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "204",
					description = "Suppression reussie"
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
		int result = playerService.deleteById(id);
		if (result == 0) {
			throw new IdNotFoundException("Pas de joueur avec cet id");
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
