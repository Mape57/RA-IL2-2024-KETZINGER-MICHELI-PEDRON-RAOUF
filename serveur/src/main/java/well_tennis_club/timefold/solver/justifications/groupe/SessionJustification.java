package well_tennis_club.timefold.solver.justifications.groupe;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;

@Getter
@AllArgsConstructor
public class SessionJustification implements ConstraintJustification {
	protected final Session session;
	protected final HardSoftScore score;
	protected final String description;
}
