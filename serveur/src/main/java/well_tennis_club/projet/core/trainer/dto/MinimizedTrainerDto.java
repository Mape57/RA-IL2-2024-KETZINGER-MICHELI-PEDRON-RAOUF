package well_tennis_club.projet.core.trainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.core.disponibility.dto.DisponibilityDto;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MinimizedTrainerDto {
	@Schema(name = "id", example = "1")
	private UUID id;
	@Schema(name = "name", example = "Nadal")
	private String name;
	@Schema(name = "surname", example = "Toni")
	private String surname;
	@Schema(name = "supLevel", example = "5")
	private int supLevel;
	@Schema(name = "infLevel", example = "3")
	private int infLevel;
	@Schema(name = "supAge", example = "50")
	private int supAge;
	@Schema(name = "infAge", example = "30")
	private int infAge;

	private List<DisponibilityDto> disponibilities;
}
