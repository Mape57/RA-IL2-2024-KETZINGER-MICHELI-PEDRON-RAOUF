package well_tennis_club.projet.disponibility;

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
@Table(name = "disponibility")
public class DisponibilityEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(generator = "disponibility_seq_gen")
    @SequenceGenerator(name = "disponibility_seq_gen",sequenceName = "disponibility_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "open")
    private String open;

    @Column(name = "close")
    private String close;
}
