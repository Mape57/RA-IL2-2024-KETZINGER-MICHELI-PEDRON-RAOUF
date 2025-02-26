package well_tennis_club.projet.core.trainer.controller;

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
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import well_tennis_club.projet.core.trainer.dto.CreateTrainerDto;
import well_tennis_club.projet.core.trainer.dto.PasswordResetDto;
import well_tennis_club.projet.core.trainer.dto.PutTrainerDto;
import well_tennis_club.projet.core.trainer.dto.TrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.projet.core.trainer.mapper.CreateTrainerMapper;
import well_tennis_club.projet.core.trainer.mapper.PutTrainerMapper;
import well_tennis_club.projet.core.trainer.mapper.TrainerMapper;
import well_tennis_club.projet.core.trainer.service.ResetTokenService;
import well_tennis_club.projet.core.trainer.service.TrainerService;
import well_tennis_club.projet.exception.IdNotFoundException;
import well_tennis_club.projet.exception.InvalidTokenException;
import well_tennis_club.projet.exception.PasswordNotMatching;
import well_tennis_club.projet.tool.ApiErrorResponse;
import well_tennis_club.projet.tool.MailFactory;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("trainers")
@Transactional
@CrossOrigin
public class TrainerController {
	private final TrainerService trainerService;
	private final ResetTokenService resetTokenService;
	private final MailFactory mailFactory;
	private final JavaMailSender mailSender;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public TrainerController(TrainerService trainerService, ResetTokenService resetTokenService, MailFactory mailFactory, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
		this.trainerService = trainerService;
		this.resetTokenService = resetTokenService;
		this.mailFactory = mailFactory;
		this.mailSender = mailSender;
		this.passwordEncoder = passwordEncoder;
	}

	// ========================= GET ========================= //
	@Operation(
			summary = "Récupère les entraîneurs",
			description = "Retourne les entraîneurs",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Récupération réussie",
					content = @Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = TrainerDto.class))
					)
			)
	})
	@GetMapping
	public ResponseEntity<List<TrainerDto>> getAllTrainers() {
		List<TrainerEntity> list = trainerService.getAllTrainers();
		return ResponseEntity.ok(TrainerMapper.INSTANCE.mapToListDTO(list));
	}

	@Operation(
			summary = "Récupère un entraîneur",
			description = "Retourne l'entraîneur avec l'id fourni",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successfully retrieved",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = TrainerDto.class)
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
					description = "Trainer not found",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@GetMapping("/{id}")
	public ResponseEntity<TrainerDto> getTrainer(@PathVariable UUID id) {
		TrainerEntity trainer = trainerService.getTrainerById(id);
		if (trainer == null) {
			throw new IdNotFoundException("Pas d'entraîneur avec cet id");
		} else {
			return ResponseEntity.ok(TrainerMapper.INSTANCE.mapToDTO(trainer));
		}
	}

	// ========================= POST ========================= //
	@Operation(
			summary = "Crée un entraineur",
			description = "Crée un entraineur avec les informations fournies",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Création réussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = TrainerDto.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Le DTO envoyé n'est pas valide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@PostMapping
	public ResponseEntity<TrainerDto> createTrainer(@Valid @RequestBody CreateTrainerDto trainerDto) {
		TrainerEntity trainer = CreateTrainerMapper.INSTANCE.mapToEntity(trainerDto);

		// le mot de passe est généré aléatoirement (l'entraineur devra le changer avec la reinitalisation)
		String password = UUID.randomUUID().toString();
		trainer.setPassword(passwordEncoder.encode(password));
		trainer = trainerService.createTrainer(trainer);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(trainer.getId())
				.toUri();

		return ResponseEntity.created(location).body(TrainerMapper.INSTANCE.mapToDTO(trainer));
	}

	// ========================= PUT ========================= //
	@Operation(
			summary = "Met a jour l'entraineur",
			description = "Met a jour l'entraineur avec l'id spécifié",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Mise à jour reussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = TrainerDto.class)
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
					description = "Pas d'entraineur avec cet id",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@PutMapping("/{id}")
	public ResponseEntity<TrainerDto> updateTrainer(@PathVariable UUID id, @Valid @RequestBody PutTrainerDto trainerDto) {
		TrainerEntity trainer = trainerService.getTrainerById(id);
		if (trainer == null) {
			throw new IdNotFoundException("Pas d'entraineur avec cet id");
		} else {
			trainer = PutTrainerMapper.INSTANCE.mapToEntity(trainerDto);
			trainer.setId(id);
			trainer = trainerService.updateTrainer(trainer);
			return ResponseEntity.ok(TrainerMapper.INSTANCE.mapToDTO(trainer));
		}
	}

	// ========================= DELETE ========================= //
	@Operation(
			summary = "Supprime un entraîneur",
			description = "Supprime l'entraîneur avec l'id fourni",
			security = @SecurityRequirement(name = "bearerAuth"))
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
					description = "Pas d'entraîneur avec cet id",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTrainer(@PathVariable UUID id) {
		int result = trainerService.deleteById(id);
		if (result == 0) {
			throw new IdNotFoundException("Pas d'entraîneur avec cet id");
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	// ========================= RESET ACTIONS ========================= //
	@Operation(
			summary = "Débute la réinitialisation de mot de passe",
			description = "Envoie un mail de réinitialisation de mot de passe à l'adresse mail fournie"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Mail envoyé"
			),
			@ApiResponse(
					responseCode = "404",
					description = "Trainer not found",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@PostMapping("/resetPassword")
	public ResponseEntity<Void> resetPassword(@RequestBody String trainerMail) {
		TrainerEntity trainer = trainerService.getTrainerByEmail(trainerMail);
		if (trainer == null) {
			throw new IdNotFoundException("Pas d'entraîneur avec cet email");
		}

		String token = UUID.randomUUID().toString();
		resetTokenService.createResetTokenForTrainer(trainer, token);
		mailSender.send(mailFactory.constructResetTokenMail(token, trainerMail));
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Change le mot de passe",
			description = "Change le mot de passe de l'entraineur avec le token fourni"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Mot de passe changé"
			),
			@ApiResponse(
					responseCode = "400",
					description = "Les mots de passe ne correspondent pas",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "401",
					description = "Le token de réinitialisation n'est pas valide",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiErrorResponse.class)
					)
			)
	})
	@PatchMapping("/changePassword")
	public ResponseEntity<Void> changePassword(@RequestBody PasswordResetDto passwordResetDto) {
		if (!passwordResetDto.getPassword().equals(passwordResetDto.getConfirmPassword())) {
			throw new PasswordNotMatching("Les mots de passe ne correspondent pas");
		}

		if (!resetTokenService.isResetTokenValid(passwordResetDto.getToken())) {
			throw new InvalidTokenException("Le token de réinitialisation n'est pas valide");
		}

		TrainerEntity trainer = resetTokenService.getTrainerByToken(passwordResetDto.getToken());
		trainer.setPassword(passwordEncoder.encode(passwordResetDto.getPassword()));
		trainerService.updateTrainer(trainer);
		resetTokenService.deleteByToken(passwordResetDto.getToken());
		return ResponseEntity.ok().build();
	}
}
