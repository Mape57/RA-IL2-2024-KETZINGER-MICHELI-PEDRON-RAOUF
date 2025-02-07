package well_tennis_club.projet.time;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "time")
public class TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "day_week")
    private String dayWeek;

    @Column(name = "start")
    private String start;

    @Column(name = "stop")
    private String stop;
}
