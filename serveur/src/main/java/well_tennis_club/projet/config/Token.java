package well_tennis_club.projet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Token {

    @Value("${token}")
    private String auth;

    public String getToken() {
        return auth;
    }
}
