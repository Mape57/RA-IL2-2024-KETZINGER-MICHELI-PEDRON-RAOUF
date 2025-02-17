package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Trainer;

/**
 * Justification d'une contrainte de nombre d'heures hebdomadaires d'un entraîneur non respectée.
 */
public record TrainerweeklyMinutesJustification(Trainer trainer, Integer weeklyMinutes, HardSoftScore score, String description) implements ConstraintJustification {
	public TrainerweeklyMinutesJustification(Trainer trainer, Integer weeklyMinutes, HardSoftScore score) {
		this(trainer, weeklyMinutes, score, getDescription(trainer, weeklyMinutes, score));
	}

	private static String getDescription(Trainer trainer, Integer weeklyMinutes, HardSoftScore score) {
		return String.format("Entraîneur %s n'a pas le bon nombre d'heure (total : %d) : %s",
				trainer.getId(), weeklyMinutes, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
