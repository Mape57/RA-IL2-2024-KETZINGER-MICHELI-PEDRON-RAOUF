package well_tennis_club.projet.core.time;

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
@Table(name = "time")
public class TimeEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 2405172041950251807L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "day_week")
	private int dayWeek;

	@Column(name = "start")
	private String start;

	@Column(name = "stop")
	private String stop;
}
