package well_tennis_club.projet.disponibilityTrainer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Embeddable
public class DisponibilityTrainerKey {
    @Column(name = "id_trainer")
    private UUID idTrainer;
    @Column(name = "id_disponibility")
    private UUID idDisponibility;
}
