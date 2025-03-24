package well_tennis_club.projet.core.player.dto;

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
public class MinimizedPlayerDto {
	@Schema(name = "id", example = "1")
	private UUID id;
	@Schema(name = "name", example = "Nadal")
	private String name;
	@Schema(name = "surname", example = "Rafael")
	private String surname;
	@Schema(name = "level", example = "19")
	private Long level;
	@Schema(name = "birthday", example = "1986-06-03")
	private String birthday;

	private List<DisponibilityDto> disponibilities;
}
