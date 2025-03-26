package well_tennis_club.timefold.solver.justifications.groupe;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import well_tennis_club.timefold.domain.Trainer;

@Getter
@AllArgsConstructor
public class TrainerJustification implements ConstraintJustification {
	protected final Trainer trainer;
	protected final HardSoftScore score;
	protected final String description;
}
