package well_tennis_club.projet.disponibilityPlayer;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "disponibility_player")
public class DisponibilityPlayerEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @EmbeddedId
    private DisponibilityPlayerKey disponibilityPlayerKey;
}
