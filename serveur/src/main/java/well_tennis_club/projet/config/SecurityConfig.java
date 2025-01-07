package well_tennis_club.projet.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Token token;

    @Autowired
    public SecurityConfig(Token token) {
        this.token = token;
    }
    public static final List<String> PERMIT_ALL_MATCHERS = List.of("/openapi/v3/api-docs/**", "/openapi/swagger-ui/**","/ws/**", "/discord/**");

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers(PERMIT_ALL_MATCHERS.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new AuthenticationFilter(token), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Customize Swagger UI to allow to set X-API-KEY header
     * @return  the operation customizer with the extra field
     */
    @Bean
    public OperationCustomizer customGlobalHeaders() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            // Add X-API-KEY header
            Parameter apiKey = new Parameter()
                    .in(ParameterIn.HEADER.toString())
                    .schema(new StringSchema())
                    .name("X-API-KEY")
                    .description("API key for authorization")
                    .required(true);
            operation.addParametersItem(apiKey);
            return operation;
        };
    }

}