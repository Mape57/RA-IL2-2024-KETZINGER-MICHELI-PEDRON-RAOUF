package well_tennis_club.projet.timeCourt;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Embeddable
public class TimeCourtKey {
    @Column(name = "id_court")
    private UUID idCourt;

    @Column(name = "id_time")
    private UUID idTime;
}
