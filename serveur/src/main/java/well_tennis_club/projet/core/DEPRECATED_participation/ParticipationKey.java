package well_tennis_club.projet.core.DEPRECATED_participation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Embeddable
@Deprecated
public class ParticipationKey {
	@Column(name = "id_player")
	private UUID idPlayer;

	@Column(name = "id_session")
	private UUID idSession;
}
