package well_tennis_club.projet.disponibilityCoach;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Embeddable
public class DisponibilityCoachKey {
    @Column(name = "id_coach")
    private UUID idCoach;
    @Column(name = "id_disponibility")
    private UUID idDisponibility;
}
