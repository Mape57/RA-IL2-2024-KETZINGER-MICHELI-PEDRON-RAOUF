package well_tennis_club.projet.coach;

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
@Table(name = "coach")
public class CoachEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(generator = "coach_seq_gen")
    @SequenceGenerator(name = "coach_seq_gen",sequenceName = "coach_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "levels")
    private String levels;

    @Column(name = "ages")
    private String ages;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "disponibility_coach",
            joinColumns = @JoinColumn(name = "id_coach",  referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_disponibility", referencedColumnName = "id")
    )
    private List<DisponibilityEntity> disponibitities = new ArrayList<>();

}
