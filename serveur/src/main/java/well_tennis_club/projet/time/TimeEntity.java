package well_tennis_club.projet.time;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "time")
public class TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(generator = "time_seq_gen")
    @SequenceGenerator(name = "time_seq_gen",sequenceName = "time_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "start")
    private String start;

    @Column(name = "stop")
    private String stop;
}
