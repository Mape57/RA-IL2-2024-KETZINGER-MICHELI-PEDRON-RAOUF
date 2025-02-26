package well_tennis_club.projet.core.trainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthResponseDto {
	@Schema(name = "token", example = "eyJhbGciOiJIUzI1NiJ9...")
	private String token;

	@Schema(name = "type", example = "Bearer")
	private String type;

	@Schema(name = "role", example = "ADMIN")
	private String role;
}
