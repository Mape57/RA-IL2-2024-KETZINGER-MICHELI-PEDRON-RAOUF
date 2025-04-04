package well_tennis_club.projet.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import well_tennis_club.projet.core.trainer.service.ConnectionService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final ConnectionService connectionService;
	private final JwtUtils jwtUtils;

	@Autowired
	public SecurityConfig(ConnectionService connectionService, JwtUtils jwtUtils) {
		this.connectionService = connectionService;
		this.jwtUtils = jwtUtils;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(connectionService).passwordEncoder(passwordEncoder);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, Environment environment) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth ->
						auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
								.requestMatchers("/openapi/v3/api-docs/**", "/openapi/swagger-ui/**", "/ws/**",
										"/auth/*", "/inscription", "/inscription/verify", "/trainers/change-password",
										"/trainers/reset-password").permitAll()
								.requestMatchers(HttpMethod.GET, "/trainers").hasAnyRole("TRAINER", "ADMIN")
								.requestMatchers(HttpMethod.GET, "/courts").hasAnyRole("TRAINER", "ADMIN")
								.requestMatchers(HttpMethod.GET, "/players").hasAnyRole("TRAINER", "ADMIN")
								.requestMatchers(HttpMethod.GET, "/sessions").hasAnyRole("TRAINER", "ADMIN")
								.requestMatchers(HttpMethod.GET,"/constraints").hasAnyRole("TRAINER", "ADMIN")
								.anyRequest().hasRole("ADMIN"))
				.cors(cors -> cors.configurationSource(corsConfigurationSource(environment)))
				.addFilterBefore(new JwtFilter(connectionService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
				.build();
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource(Environment environment) {
		CorsConfiguration configuration = new CorsConfiguration();
		String baseUrl = environment.getProperty("base_url");
		configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://192.168.1.14:5173","http://172.20.64.1:5173","http://localhost:3000", baseUrl == null ? "http://localhost:5173" : baseUrl));
		configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public OpenAPI customOpenAPI() {
		SecurityScheme bearerAuthScheme = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT")  // Optional
				.in(SecurityScheme.In.HEADER)
				.name("Authorization");

		return new OpenAPI()
				.components(
						new Components().addSecuritySchemes("bearerAuth", bearerAuthScheme)
				);
	}
}