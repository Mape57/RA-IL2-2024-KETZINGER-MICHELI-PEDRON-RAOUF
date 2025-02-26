package well_tennis_club.projet.core.court;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import well_tennis_club.projet.core.time.TimeDto;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CourtDto {
	@Schema(name = "id", example = "1")
	@NotNull(message = "L'id (id) ne peut pas être nul")
	private UUID id;

	@Schema(name = "name", example = "Philippe Chatrier")
	@NotBlank(message = "Le nom (name) ne peut pas être vide")
	private String name;

	@Valid
	private List<TimeDto> times;
}
