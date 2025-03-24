package well_tennis_club.projet.core.solver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class JustificationsDto {
	protected Type type;

	protected enum Type {
		TRAINER, PLAYER, SESSION
	}
}
