package well_tennis_club.projet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import well_tennis_club.projet.config.JwtUtils;
import well_tennis_club.projet.dto.UserConnectionDto;
import well_tennis_club.projet.service.ConnectionService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final ConnectionService connectionService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;

	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserConnectionDto userConnectionDto) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userConnectionDto.getEmail(), userConnectionDto.getPassword()));
			if (authentication.isAuthenticated()) {
				Map<String, Object> authData = new HashMap<>();
				authData.put("token", jwtUtils.generateToken(userConnectionDto.getEmail()));
				authData.put("type", "Bearer");
				return ResponseEntity.ok(authData);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
		}
	}
}
