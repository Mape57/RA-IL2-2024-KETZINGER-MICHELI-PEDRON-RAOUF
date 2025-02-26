package well_tennis_club.projet.core.court;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import well_tennis_club.projet.core.time.TimeEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "court")
public class CourtEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 2405172041950251807L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinTable(
			name = "time_court",
			joinColumns = @JoinColumn(name = "id_court", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_time", referencedColumnName = "id")
	)
	private List<TimeEntity> times = new ArrayList<>();
}
