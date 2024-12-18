package well_tennis_club.projet.player;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import well_tennis_club.projet.disponibility.DisponibilityEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "player")
public class PlayerEntity implements Serializable{
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(generator = "player_seq_gen")
    @SequenceGenerator(name = "player_seq_gen",sequenceName = "player_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "courses")
    private Long courses;

    @Column(name = "level")
    private Long level;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "disponibility_player",
            joinColumns = @JoinColumn(name = "id_player",  referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_disponibility", referencedColumnName = "id")
    )
    private List<DisponibilityEntity> disponibitities = new ArrayList<>();
}
