package well_tennis_club.timefold.solver.justifications.groupe;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import well_tennis_club.timefold.domain.Player;

@Getter
@AllArgsConstructor
public class PlayerJustification implements ConstraintJustification {
	protected final Player player;
	protected final HardSoftScore score;
	protected final String description;
}
