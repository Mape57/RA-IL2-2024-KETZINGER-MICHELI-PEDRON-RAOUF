package well_tennis_club.projet.core.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.core.court.CourtDto;
import well_tennis_club.projet.core.player.dto.PlayerDto;
import well_tennis_club.projet.core.trainer.dto.TrainerDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreateSessionDto {
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

	@Valid
	private CourtDto idCourt;

	@Valid
	private TrainerDto idTrainer;

	@Valid
	private List<PlayerDto> players;
}
