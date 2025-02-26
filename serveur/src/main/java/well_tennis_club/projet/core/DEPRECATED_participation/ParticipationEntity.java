package well_tennis_club.projet.core.DEPRECATED_participation;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "participation")
@Deprecated
public class ParticipationEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 2405172041950251807L;

	@EmbeddedId
	private ParticipationKey participationKey;
}
