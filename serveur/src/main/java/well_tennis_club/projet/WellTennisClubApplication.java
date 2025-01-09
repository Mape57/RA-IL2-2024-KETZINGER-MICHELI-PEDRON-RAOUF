package well_tennis_club.projet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication
public class WellTennisClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellTennisClubApplication.class, args);
	}

}
