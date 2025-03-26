package well_tennis_club.timefold.solver.justifications.groupe;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;

import java.util.List;

@Getter
@AllArgsConstructor
public class SessionsJustification implements ConstraintJustification {
	protected final List<Session> sessions;
	protected final HardSoftScore score;
	protected final String description;
}
