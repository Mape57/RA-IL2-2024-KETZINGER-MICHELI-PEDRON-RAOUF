package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de préférence de niveau d'un entraîneur non respectée.
 */
public record TrainerPreferedLevelJustification(Session session, Integer trainerLevelOverflow, HardSoftScore score,
												String description) implements ConstraintJustification {
	public TrainerPreferedLevelJustification(Session session, Integer trainerLevelOverflow, HardSoftScore score) {
		this(session, trainerLevelOverflow, score, getDescription(session, trainerLevelOverflow, score));
	}

	private static String getDescription(Session session, Integer trainerLevelOverflow, HardSoftScore score) {
		return String.format("Depassement de niveau (+%d) de %s pour %s : %s",
				trainerLevelOverflow, session.getTrainer().getId(), session, score);
	}

	@Override
	public String toString() {
		return description;
	}
}
