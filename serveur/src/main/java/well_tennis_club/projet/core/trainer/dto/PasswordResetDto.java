package well_tennis_club.projet.core.trainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PasswordResetDto implements Serializable {
	@Schema(name = "token", example = "74d26c7c-aed6-4338-831c-d8f46f06b7a3")
	@NotBlank(message = "Le token (token) est obligatoire")
	private String token;

	@Schema(name = "password", example = "password")
	@NotBlank(message = "Le mot de passe (password) est obligatoire")
	private String password;

	@Schema(name = "confirmPassword", example = "password")
	@NotBlank(message = "La confirmation du mot de passe (confirmPassword) est obligatoire")
	private String confirmPassword;
}
