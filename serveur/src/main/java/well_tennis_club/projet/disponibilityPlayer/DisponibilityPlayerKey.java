package well_tennis_club.projet.disponibilityPlayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DisponibilityPlayerKey {

    @Column(name = "id_player")
    private Long idPlayer;

    @Column(name = "id_disponibility")
    private Long idDisponibility;

}
