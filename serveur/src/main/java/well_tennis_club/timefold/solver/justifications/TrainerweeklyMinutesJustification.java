package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Trainer;
import well_tennis_club.timefold.solver.justifications.groupe.TrainerJustification;

/**
 * Justification d'une contrainte de nombre d'heures hebdomadaires d'un entraîneur non respectée.
 */
@Getter
public class TrainerweeklyMinutesJustification extends TrainerJustification {
	private final Integer weeklyMinutes;

	public TrainerweeklyMinutesJustification(Trainer trainer, Integer weeklyMinutes, HardSoftScore score) {
		this(trainer, weeklyMinutes, score, getDescription(trainer, weeklyMinutes, score));
	}

	public TrainerweeklyMinutesJustification(Trainer trainer, Integer weeklyMinutes, HardSoftScore score, String description) {
		super(trainer, score, description);
		this.weeklyMinutes = weeklyMinutes;
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
