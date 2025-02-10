package well_tennis_club.projet.trainer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.userdetails.UserDetails;
import well_tennis_club.projet.disponibility.DisponibilityEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "trainer")
public class TrainerEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "sup_level")
    private int supLevel;

    @Column(name = "inf_level")
    private int infLevel;

    @Column(name = "sup_age")
    private int supAge;

    @Column(name = "inf_age")
    private int infAge;

    @Column(name = "sup_weekly_minutes")
    private int supWeeklyMinutes;

    @Column(name = "inf_weekly_minutes")
    private int infWeeklyMinutes;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "part_time")
    private boolean partTime;

    @Column(name = "admin")
    private boolean admin;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "disponibility_trainer",
            joinColumns = @JoinColumn(name = "id_trainer",  referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_disponibility", referencedColumnName = "id")
    )
    private List<DisponibilityEntity> disponibitities = new ArrayList<>();

}
