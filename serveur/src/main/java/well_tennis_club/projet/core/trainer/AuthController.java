package well_tennis_club.projet.core.trainer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import well_tennis_club.projet.config.JwtUtils;
import well_tennis_club.projet.core.trainer.dto.AuthResponseDto;
import well_tennis_club.projet.core.trainer.dto.UserConnectionDto;
import well_tennis_club.projet.exception.FailedAuthException;

import javax.security.auth.login.FailedLoginException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;

	@Operation(
			summary = "Permet de se connecter",
			description = "Permet de se connecter et de récupérer un token"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Connexion réussie",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = AuthResponseDto.class)
					)
			)
	}
	)
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody UserConnectionDto userConnectionDto) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userConnectionDto.getEmail(), userConnectionDto.getPassword());

		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(token);
		} catch (AuthenticationException e) {
			throw new FailedAuthException("Vos identifiants sont incorrects");
		}

		if (!authentication.isAuthenticated()) {
			throw new FailedAuthException("L'authentification a échoué");
		}
		AuthResponseDto response = new AuthResponseDto();
		response.setToken(jwtUtils.generateToken(userConnectionDto.getEmail()));
		response.setType("Bearer");
		response.setRole(authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(";")));
		return ResponseEntity.ok(response);
	}
}
