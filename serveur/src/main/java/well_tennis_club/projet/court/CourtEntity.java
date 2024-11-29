package well_tennis_club.projet.court;

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
@Table(name = "court")
public class CourtEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(generator = "court_seq_gen")
    @SequenceGenerator(name = "court_seq_gen",sequenceName = "court_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start")
    private String start;

    @Column(name = "end")
    private String end;
}
