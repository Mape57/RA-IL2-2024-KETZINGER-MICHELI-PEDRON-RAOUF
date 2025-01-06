package well_tennis_club.projet.participation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ParticipationKey {
    @Column(name = "id_player")
    private Long idPlayer;

    @Column(name = "id_session")
    private Long idSession;
}
