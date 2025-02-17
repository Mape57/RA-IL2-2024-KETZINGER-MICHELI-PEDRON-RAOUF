package well_tennis_club.projet.session;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import well_tennis_club.projet.trainer.TrainerEntity;
import well_tennis_club.projet.court.CourtEntity;
import well_tennis_club.projet.player.PlayerEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "session")
public class SessionEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "day_week")
    private int dayWeek;

    @Column(name = "start")
    private String start;

    @Column(name = "stop")
    private String stop;

    @ManyToOne
    @JoinColumn(name = "id_court", referencedColumnName = "id")
    private CourtEntity idCourt;

    @ManyToOne
    @JoinColumn(name = "id_trainer", referencedColumnName = "id")
    private TrainerEntity idTrainer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "participation",
            joinColumns = @JoinColumn(name = "id_session",  referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_player", referencedColumnName = "id")
    )
    private List<PlayerEntity> players = new ArrayList<>();
}
