package well_tennis_club.projet.timeCourt;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class TimeCourtKey {
    @Column(name = "id_court")
    private Long idCourt;

    @Column(name = "id_time")
    private Long idTime;
}
