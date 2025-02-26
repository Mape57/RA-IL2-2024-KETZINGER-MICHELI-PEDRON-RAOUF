package well_tennis_club.projet.core.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NewSessionDto {
	@Schema(name = "dayWeek", example = "4")
	@NotNull(message = "Le jour de la semaine (dayWeek) ne peut pas être nul")
	@Min(value = 1, message = "Le jour (dayWeek) ne doit pas être inférieur à 1")
	@Max(value = 7, message = "Le jour (dayWeek) ne doit pas être supérieur à 7")
	private int dayWeek;

	@Schema(name = "start", example = "8:00")
	@NotBlank(message = "L'heure de début (start) ne peut pas être vide")
	@Pattern(
			regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]",
			message = "L'heure de début (start) doit être au format HH:mm"
	)
	private String start;

	@Schema(name = "stop", example = "10:00")
	@NotBlank(message = "L'heure de fin (stop) ne peut pas être vide")
	@Pattern(
			regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]",
			message = "L'heure de fin (stop) doit être au format HH:mm"
	)
	private String stop;

	@Schema(name = "idCourt", example = "1")
	@NotNull(message = "L'id du court (idCourt) ne peut pas être nul")
	private UUID idCourt;

	@Schema(name = "idTrainer", example = "1")
	private UUID idTrainer;

	@Schema(name = "players", example = "[1, 2, 3]")
	@NotNull(message = "La liste des joueurs (players) ne peut pas être nulle")
	private List<UUID> playerIds;
}
