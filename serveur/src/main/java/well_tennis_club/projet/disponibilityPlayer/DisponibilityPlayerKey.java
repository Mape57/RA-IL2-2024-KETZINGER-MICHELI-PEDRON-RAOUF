package well_tennis_club.projet.disponibilityPlayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Embeddable
public class DisponibilityPlayerKey {

    @Column(name = "id_player")
    private UUID idPlayer;

    @Column(name = "id_disponibility")
    private UUID idDisponibility;

}
