package well_tennis_club.projet.core.disponibility.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PutDisponibilityDto implements Serializable {
	@Schema(name = "id", example = "1")
	private UUID id;

	@Schema(name = "dayWeek", example = "5")
	@NotNull(message = "Le jour de la semaine (dayWeek) ne peut pas être nul")
	@Min(value = 1, message = "Le jour (dayWeek) ne doit pas être inférieur à 1")
	@Max(value = 7, message = "Le jour (dayWeek) ne doit pas être supérieur à 7")
	private int dayWeek;

	@Schema(name = "open", example = "8:00")
	@NotBlank(message = "L'heure d'ouverture (open) ne peut pas être vide")
	@Pattern(
			regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]",
			message = "L'heure d'ouverture (open) doit être au format HH:mm"
	)
	private String open;

	@Schema(name = "close", example = "18:00")
	@NotBlank(message = "L'heure de fermeture (close) ne peut pas être vide")
	@Pattern(
			regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]",
			message = "L'heure de fermeture (close) doit être au format HH:mm"
	)
	private String close;
}