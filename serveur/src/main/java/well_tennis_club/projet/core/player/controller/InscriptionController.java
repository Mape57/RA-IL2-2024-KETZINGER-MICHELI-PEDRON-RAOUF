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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import well_tennis_club.projet.core.disponibility.mapper.PlayerInscriptionMapper;
import well_tennis_club.projet.core.player.dto.PlayerDto;
import well_tennis_club.projet.core.player.dto.PlayerInscriptionDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
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
	private final MailSender mailSender;

	@Autowired
	public InscriptionController(PlayerService playerService, InscriptionTokenService inscriptionTokenService,
								 MailFactory mailFactory, MailSender mailSender) {
		this.playerService = playerService;
		this.inscriptionTokenService = inscriptionTokenService;
		this.mailFactory = mailFactory;
		this.mailSender = mailSender;
	}

	// ========================= POST ========================= //
	@Operation(
			summary = "Cree un token d'inscription",
			description = "Cree un token d'inscription pour un email et l'envoie par mail"
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
			// TODO @ApiResponse(responseCode = "409", description = "The email is already used")
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
			description = "Inscrit un joueur en base de donnees si le token est valide et supprime le token d'inscription associe"
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
					responseCode = "400",
					description = "Le DTO envoye n'est pas valide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(
									implementation = ApiErrorResponse.class
							)
					)
			),
			@ApiResponse(
					responseCode = "401",
					description = "Le token n'est pas valide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(
									implementation = ApiErrorResponse.class
							)
					)
			)
			// TODO @ApiResponse(responseCode = "409", description = "The email is already used")
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
