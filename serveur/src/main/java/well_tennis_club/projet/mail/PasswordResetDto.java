package well_tennis_club.projet.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordResetDto implements Serializable {
	@Schema(name = "token", example = "74d26c7c-aed6-4338-831c-d8f46f06b7a3")
	private String token;
	@Schema(name = "password", example = "password")
	private String password;
	@Schema(name = "confirmPassword", example = "password")
	private String confirmPassword;
}
