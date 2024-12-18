package well_tennis_club.projet.disponibilityCoach;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DisponibilityCoachKey {
    @Column(name = "id_coach")
    private Long idCoach;
    @Column(name = "id_disponibility")
    private Long idDisponibility;
}
