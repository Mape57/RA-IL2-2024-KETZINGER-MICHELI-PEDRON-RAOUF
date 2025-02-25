package well_tennis_club.projet.core.trainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "L'email (email) est obligatoire")
	@Email(message = "L'email (email) doit être valide")
	private String email;

	@Schema(name = "password", example = "password")
	@NotBlank(message = "Le mot de passe (password) est obligatoire")
	private String password;
}
