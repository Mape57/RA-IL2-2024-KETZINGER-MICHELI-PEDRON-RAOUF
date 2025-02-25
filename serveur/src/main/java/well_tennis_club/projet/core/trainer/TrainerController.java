package well_tennis_club.projet.core.trainer;

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
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import well_tennis_club.projet.core.trainer.dto.CreateTrainerDto;
import well_tennis_club.projet.core.trainer.dto.PasswordResetDto;
import well_tennis_club.projet.core.trainer.dto.TrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.projet.core.trainer.mapper.CreateTrainerMapper;
import well_tennis_club.projet.core.trainer.mapper.TrainerMapper;
import well_tennis_club.projet.core.trainer.service.ResetTokenService;
import well_tennis_club.projet.core.trainer.service.TrainerService;
import well_tennis_club.projet.exception.IdNotFoundException;
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
	private final MailSender mailSender;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public TrainerController(TrainerService trainerService, ResetTokenService resetTokenService, MailFactory mailFactory, MailSender mailSender, PasswordEncoder passwordEncoder) {
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

	// ========================= PATCH ========================= //
	@Operation(summary = "Update trainer", description = "Update trainer with id",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully patched"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Trainer was not update")
	})
	@PatchMapping("/{id}")
	public TrainerDto updateTrainer(@PathVariable UUID id, @RequestBody TrainerDto trainerDto) {
		TrainerDto trainer = TrainerMapper.INSTANCE.mapToDTO(trainerService.getTrainerById(id));
		if (trainer == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Trainer not found"
			);
		} else {
			TrainerDto modif = trainerDto;
			modif.setId(trainer.getId());
			return TrainerMapper.INSTANCE.mapToDTO(trainerService.updateTrainer(TrainerMapper.INSTANCE.mapToEntity(modif)));
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
	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody String trainerMail) {
		TrainerEntity trainer = trainerService.getTrainerByEmail(trainerMail);
		if (trainer == null) {
			return new ResponseEntity<>("Trainer not found", HttpStatus.NOT_FOUND);
		}

		String token = UUID.randomUUID().toString();
		resetTokenService.createResetTokenForTrainer(trainer, token);
		mailSender.send(mailFactory.constructResetTokenMail(token, trainerMail));
		return new ResponseEntity<>("Mail sent", HttpStatus.OK);
	}

	@PatchMapping("/changePassword")
	public String changePassword(@RequestBody PasswordResetDto passwordResetDto) {
		if (!passwordResetDto.getPassword().equals(passwordResetDto.getConfirmPassword())) {
			return "Passwords do not match";
		}

		if (!resetTokenService.isResetTokenValid(passwordResetDto.getToken())) {
			return "Token invalid";
		}

		TrainerEntity trainer = resetTokenService.getTrainerByToken(passwordResetDto.getToken());
		trainer.setPassword(passwordEncoder.encode(passwordResetDto.getPassword()));
		trainerService.updateTrainer(trainer);
		resetTokenService.deleteByToken(passwordResetDto.getToken());
		return "Password changed";
	}
}
