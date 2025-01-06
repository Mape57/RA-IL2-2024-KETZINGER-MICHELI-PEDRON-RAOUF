package well_tennis_club.projet.session;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import well_tennis_club.projet.coach.CoachEntity;
import well_tennis_club.projet.court.CourtEntity;
import well_tennis_club.projet.player.PlayerEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "session")
public class SessionEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(generator = "session_seq_gen")
    @SequenceGenerator(name = "session_seq_gen",sequenceName = "session_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "start")
    private String start;

    @Column(name = "stop")
    private String stop;

    @ManyToOne
    @JoinColumn(name = "id_court", referencedColumnName = "id")
    private CourtEntity idCourt;

    @ManyToOne
    @JoinColumn(name = "id_coach", referencedColumnName = "id")
    private CoachEntity idCoach;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "participation",
            joinColumns = @JoinColumn(name = "id_session",  referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_player", referencedColumnName = "id")
    )
    private List<PlayerEntity> players = new ArrayList<>();
}
