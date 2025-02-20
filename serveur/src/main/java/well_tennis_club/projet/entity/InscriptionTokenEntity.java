package well_tennis_club.projet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "inscription_token")
public class InscriptionTokenEntity {
	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String token;
	private String email;
	private Date expirationDate;

	public InscriptionTokenEntity(String email, String token) {
		this.email = email;
		this.token = token;
		this.expirationDate = new Date(System.currentTimeMillis() + EXPIRATION * 60 * 1000);
	}
}
