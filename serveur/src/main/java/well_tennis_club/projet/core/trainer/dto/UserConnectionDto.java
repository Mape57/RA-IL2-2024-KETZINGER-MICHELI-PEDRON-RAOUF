package well_tennis_club.projet.core.trainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UserConnectionDto implements Serializable {
	@Schema(name = "email", example = "oui@oui.non")
	private String email;
	@Schema(name = "password", example = "password")
	private String password;
}
