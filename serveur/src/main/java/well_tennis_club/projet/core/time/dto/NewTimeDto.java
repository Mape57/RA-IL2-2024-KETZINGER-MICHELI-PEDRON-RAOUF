package well_tennis_club.projet.core.time.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NewTimeDto {
	@Schema(name = "id", example = "1")
	private UUID id;

	@Schema(name = "dayWeek", example = "5")
	@NotNull(message = "Le jour de la semaine (dayWeek) ne peut pas être nul")
	@Min(value = 1, message = "Le jour (dayWeek) ne doit pas être inférieur à 1")
	@Max(value = 7, message = "Le jour (dayWeek) ne doit pas être supérieur à 7")
	private int dayWeek;

	@Schema(name = "start", example = "8:00")
	@NotBlank(message = "L'heure d'ouverture (start) ne peut pas être vide")
	@Pattern(
			regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]",
			message = "L'heure d'ouverture (start) doit être au format HH:mm"
	)
	private String start;

	@Schema(name = "stop", example = "18:00")
	@NotBlank(message = "L'heure de fermeture (stop) ne peut pas être vide")
	@Pattern(
			regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]",
			message = "L'heure de fermeture (stop) doit être au format HH:mm"
	)
	private String stop;
}
