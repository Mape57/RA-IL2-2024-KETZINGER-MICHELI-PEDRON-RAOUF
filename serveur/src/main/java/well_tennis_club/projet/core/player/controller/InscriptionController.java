package well_tennis_club.projet.core.player.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import well_tennis_club.projet.core.player.dto.PlayerDto;
import well_tennis_club.projet.core.player.dto.PlayerInscriptionDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.player.mapper.PlayerInscriptionMapper;
import well_tennis_club.projet.core.player.mapper.PlayerMapper;
import well_tennis_club.projet.core.player.service.InscriptionTokenService;
import well_tennis_club.projet.core.player.service.PlayerService;
import well_tennis_club.projet.exception.InvalidTokenException;
import well_tennis_club.projet.tool.ApiErrorResponse;
import well_tennis_club.projet.tool.MailFactory;

import java.net.URI;
import java.util.UUID;

@RestController
@Transactional
@RequestMapping("inscription")
@CrossOrigin
public class InscriptionController {
	private final PlayerService playerService;
	private final InscriptionTokenService inscriptionTokenService;
	private final MailFactory mailFactory;
	private final JavaMailSender mailSender;

	@Autowired
	public InscriptionController(PlayerService playerService, InscriptionTokenService inscriptionTokenService,
								 MailFactory mailFactory, JavaMailSender mailSender) {
		this.playerService = playerService;
		this.inscriptionTokenService = inscriptionTokenService;
		this.mailFactory = mailFactory;
		this.mailSender = mailSender;
	}

	// ========================= POST ========================= //
	@Operation(
			summary = "Crée un token d'inscription pour un email et l'envoie par mail",
			description = "Crée un token d'inscription pour un email et l'envoie par mail"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Mail de confirmation envoyé"
			),
			@ApiResponse(
					responseCode = "400",
					description = "Le DTO envoyé n'est pas valide",
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
	@PostMapping("/verify")
	public ResponseEntity<String> inscrirePlayer(@Valid @RequestBody PlayerInscriptionDto playerInscriptionDto) {
		String token = UUID.randomUUID().toString();
		inscriptionTokenService.createInscriptionTokenForEmail(playerInscriptionDto.getEmail(), token);
		mailSender.send(mailFactory.constructInscriptionMail(token, playerInscriptionDto));

		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Permet l'inscription d'un joueur",
			description = "Inscrit un joueur en base de données et supprime le token d'inscription correspondant"
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
					description = "Le DTO envoyé n'est pas valide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(
									implementation = ApiErrorResponse.class
							)
					)
			),
			@ApiResponse(
					responseCode = "403",
					description = "Le token n'est pas valide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(
									implementation = ApiErrorResponse.class
							)
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
	public ResponseEntity<PlayerDto> inscrirePlayer(@RequestHeader("Authorization") String token, @Valid @RequestBody PlayerInscriptionDto player) {
		if (!inscriptionTokenService.isTokenValid(token)) {
			throw new InvalidTokenException("Le token d'inscription n'est pas valide");
		}

		PlayerEntity entity = PlayerInscriptionMapper.INSTANCE.mapToEntity(player);
		entity = playerService.createPlayer(entity);

		inscriptionTokenService.deleteByToken(token);

		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/players/{id}")
				.buildAndExpand(entity.getId())
				.toUri();

		return ResponseEntity.created(location).body(PlayerMapper.INSTANCE.mapToDTO(entity));
	}
}
