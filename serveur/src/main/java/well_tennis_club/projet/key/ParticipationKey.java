package well_tennis_club.projet.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Embeddable
public class ParticipationKey {
	@Column(name = "id_player")
	private UUID idPlayer;

	@Column(name = "id_session")
	private UUID idSession;
}
