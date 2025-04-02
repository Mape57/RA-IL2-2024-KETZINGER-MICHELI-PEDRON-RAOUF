package well_tennis_club.projet.core.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MinimizedSessionDto {
	@Schema(name = "id", example = "1")
	private UUID id;
	@Schema(name = "dayWeek", example = "4")
	private int dayWeek;
	@Schema(name = "start", example = "8:00")
	private String start;
	@Schema(name = "stop", example = "10:00")
	private String stop;
	@Schema(name = "court", example = "Terrain 1")
	private String court;
}
