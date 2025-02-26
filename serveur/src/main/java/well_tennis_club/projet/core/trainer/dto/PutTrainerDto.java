package well_tennis_club.projet.core.trainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.core.disponibility.dto.PutDisponibilityDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PutTrainerDto {
	@Schema(name = "name", example = "Nadal")
	@NotBlank(message = "Le nom (name) est obligatoire")
	private String name;

	@Schema(name = "surname", example = "Toni")
	@NotBlank(message = "Le prénom (surname) est obligatoire")
	private String surname;

	@Schema(name = "supLevel", example = "5")
	@NotNull(message = "Le niveau minimum (supLevel) est obligatoire")
	@Min(value = 0, message = "Le niveau maximum (supLevel) ne doit pas être inférieur a 0")
	@Max(value = 19, message = "Le niveau maximum (supLevel) ne doit pas être supérieur a 19")
	private int supLevel;

	@Schema(name = "infLevel", example = "3")
	@NotNull(message = "Le niveau maximum (infLevel) est obligatoire")
	@Min(value = 0, message = "Le niveau minimum (infLevel) ne doit pas être inférieur a 0")
	@Max(value = 19, message = "Le niveau minimum (infLevel) ne doit pas être supérieur a 19")
	private int infLevel;

	@Schema(name = "supAge", example = "50")
	@NotNull(message = "L'âge minimum (supAge) est obligatoire")
	@Min(value = 1, message = "L'âge minimum (supAge) ne doit pas être inférieur a 1")
	@Max(value = 100, message = "L'âge minimum (supAge) ne doit pas être supérieur a 100")
	private int supAge;

	@Schema(name = "infAge", example = "30")
	@NotNull(message = "L'âge maximum (infAge) est obligatoire")
	@Min(value = 1, message = "L'âge maximum (infAge) ne doit pas être inférieur a 1")
	@Max(value = 100, message = "L'âge maximum (infAge) ne doit pas être supérieur a 100")
	private int infAge;

	@Schema(name = "supWeeklyMinutes", example = "120")
	@NotNull(message = "Le nombre d'heures minimum (supWeeklyMinutes) est obligatoire")
	@Min(value = 0, message = "Le nombre d'heures minimum (supWeeklyMinutes) ne doit pas être inférieur a 0")
	private int supWeeklyMinutes;

	@Schema(name = "infWeeklyMinutes", example = "60")
	@NotNull(message = "Le nombre d'heures maximum (infWeeklyMinutes) est obligatoire")
	@Min(value = 0, message = "Le nombre d'heures maximum (infWeeklyMinutes) ne doit pas être inférieur a 0")
	private int infWeeklyMinutes;

	@Schema(name = "email", example = "test@email.fr")
	@NotBlank(message = "L'email (email) est obligatoire")
	@Email(message = "L'email (email) doit être valide")
	private String email;

	@Schema(name = "partTime", example = "false")
	@NotNull(message = "Le temps partiel (partTime) est obligatoire")
	private boolean partTime;

	@Schema(name = "admin", example = "true")
	@NotNull(message = "Le rôle admin (admin) est obligatoire")
	private boolean admin;

	@Valid
	private List<PutDisponibilityDto> disponibilities;
}
