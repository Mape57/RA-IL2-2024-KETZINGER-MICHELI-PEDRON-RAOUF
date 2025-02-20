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
@Table(name = "reset_token")
public class ResetTokenEntity {
	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String token;

	@OneToOne(targetEntity = TrainerEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "trainer_id")
	private TrainerEntity trainer;

	private Date expirationDate;

	public ResetTokenEntity(TrainerEntity trainer, String token) {
		this.trainer = trainer;
		this.token = token;
		this.expirationDate = new Date(System.currentTimeMillis() + EXPIRATION * 60 * 1000);
	}
}
