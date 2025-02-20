package well_tennis_club.projet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "session_constraint")
public class SessionConstraintEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 2405172041950251807L;

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "inf_age")
	private int infAge;

	@Column(name = "sup_age")
	private int supAge;

	@Column(name = "inf_level")
	private int infLevel;

	@Column(name = "sup_level")
	private int supLevel;

	@Column(name = "inf_group")
	private int infGroup;

	@Column(name = "sup_group")
	private int supGroup;

	@Column(name = "age_diff")
	private int ageDiff;

	@Column(name = "level_diff")
	private int levelDiff;

	@Column(name = "duration")
	private int duration;
}
