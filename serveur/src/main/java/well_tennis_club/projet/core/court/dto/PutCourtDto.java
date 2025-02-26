package well_tennis_club.projet.core.court.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.core.time.dto.NewTimeDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PutCourtDto {
	@Schema(name = "name", example = "Philippe Chatrier")
	@NotBlank(message = "Le nom (name) ne peut pas être vide")
	private String name;

	@Valid
	private List<NewTimeDto> times;
}
