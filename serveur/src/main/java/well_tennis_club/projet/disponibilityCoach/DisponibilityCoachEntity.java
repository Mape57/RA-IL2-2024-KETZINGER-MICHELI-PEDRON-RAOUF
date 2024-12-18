package well_tennis_club.projet.disponibilityCoach;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.disponibilityPlayer.DisponibilityPlayerKey;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "disponibility_coach")
public class DisponibilityCoachEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @EmbeddedId
    private DisponibilityCoachKey disponibilityCoachKey;
}
