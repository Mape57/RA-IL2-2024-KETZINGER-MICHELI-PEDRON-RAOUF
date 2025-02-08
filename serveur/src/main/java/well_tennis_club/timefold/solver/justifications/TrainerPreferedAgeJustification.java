package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de préférence d'âge d'un entraîneur non respectée.
 */
public record TrainerPreferedAgeJustification(Session session, Integer trainerAgeOverflow, HardSoftScore score,
											  String description) implements ConstraintJustification {
	public TrainerPreferedAgeJustification(Session session, Integer trainerAgeOverflow, HardSoftScore score) {
		this(session, trainerAgeOverflow, score, getDescription(session, trainerAgeOverflow, score));
	}

	private static String getDescription(Session session, Integer trainerAgeOverflow, HardSoftScore score) {
		return String.format("Depassement age (+%d) de %s pour %s  : %s",
				trainerAgeOverflow, session.getTrainer().getName(), session, score);
	}

	@Override
	public String toString() {
		return description;
	}
}
