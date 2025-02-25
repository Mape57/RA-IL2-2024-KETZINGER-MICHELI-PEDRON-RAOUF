package well_tennis_club.projet.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiErrorResponse {
	@Schema(name = "status", example = "400")
	private int status;
	@Schema(name = "message", example = "Validation Failed")
	private String message;
	@Schema(name = "data", example = "name: Name is mandatory; surname: Surname is mandatory")
	private String description;
}
