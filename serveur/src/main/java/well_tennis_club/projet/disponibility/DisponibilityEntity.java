package well_tennis_club.projet.disponibility;

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
@Table(name = "disponibility")
public class DisponibilityEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "day")
    private String day;

    @Column(name = "open")
    private String open;

    @Column(name = "close")
    private String close;
}
