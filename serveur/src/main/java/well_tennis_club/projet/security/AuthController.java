package well_tennis_club.projet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> login(@RequestBody UserConnection userConnection){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userConnection.getEmail(),userConnection.getPassword()));
            if(authentication.isAuthenticated()){
                Map<String,Object> authData = new HashMap<>();
                authData.put("token",jwtUtils.generateToken(userConnection.getEmail()));
                authData.put("type","Bearer");
                authData.put("role", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(";")));
                return ResponseEntity.ok(authData);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
        }
    }
}
