package well_tennis_club.projet.core.player.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.core.disponibility.dto.PutDisponibilityDto;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PutPlayerDto implements Serializable {
	@Schema(name = "name", example = "Nadal")
	@NotBlank(message = "Le nom (name) est obligatoire")
	private String name;

	@Schema(name = "surname", example = "Rafael")
	@NotBlank(message = "Le prénom (surname) est obligatoire")
	private String surname;

	@Schema(name = "birthday", example = "1986-06-03")
	@NotBlank(message = "L'anniversaire (birthday) est obligatoire")
	@Pattern(
			regexp = "^\\d{4}-\\d{2}-\\d{2}$",
			message = "L'anniversaire (birthday) doit être au format yyyy-MM-dd"
	)
	private String birthday;

	@Schema(name = "courses", example = "4")
	@NotNull(message = "Le nombre de cours (courses) est obligatoire")
	@Min(value = 1, message = "Le nombre de cours (courses) ne doit pas être inférieur à 1")
	private Long courses;

	@Schema(name = "level", example = "19")
	@NotNull(message = "Le niveau (level) est obligatoire")
	@Min(value = 0, message = "Le niveau (level) ne doit pas être inférieur à 0")
	@Max(value = 30, message = "Le niveau (level) ne doit pas être supérieur à 19")
	private Long level;

	@Schema(name = "email", example = "example@mail.fr")
	@NotBlank(message = "L'email (email) est obligatoire")
	@Email(message = "L'email (email) doit avoir un format valide")
	private String email;

	@Schema(name = "validate", example = "true")
	@NotNull(message = "La validation (validate) est obligatoire")
	private Boolean validate;

	@Schema(name = "phone", example = "0601020304")
	@NotNull(message = "Le téléphone (phone) est obligatoire")
	private String phone;

	@Schema(name = "phone2", example = "0601020304")
	private String phone2;

	@Schema(name = "photo",example = "true")
	@NotNull(message = "La photo (photo) est obligatoire")
	private boolean photo;

	private List<PutDisponibilityDto> disponibilities;
}