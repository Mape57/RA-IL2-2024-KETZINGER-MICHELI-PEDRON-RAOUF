package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de disponibilité d'un entraîneur non respectée.
 */
public record TrainerAvailabilityJustification(Session session, Integer sessionDuration, HardSoftScore score,
											   String description) implements ConstraintJustification {
	public TrainerAvailabilityJustification(Session session, Integer sessionDuration, HardSoftScore score) {
		this(session, sessionDuration, score, getDescription(session, sessionDuration, score));
	}

	private static String getDescription(Session session, Integer sessionDuration, HardSoftScore score) {
		return String.format("Entraineur %s indisponible pour %s : %s",
				session.getTrainer().getId(), session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}

}