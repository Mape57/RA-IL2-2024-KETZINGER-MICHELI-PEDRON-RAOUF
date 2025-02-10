package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de différence d'âge dépassée.
 */
public record AgeDifferenceJustification(Session session, Integer ageOverflow, HardSoftScore score,
										 String description) implements ConstraintJustification {
	public AgeDifferenceJustification(Session session, Integer ageOverflow, HardSoftScore score) {
		this(session, ageOverflow, score, getDescription(session, ageOverflow, score));
	}

	private static String getDescription(Session session, Integer ageOverflow, HardSoftScore score) {
		return String.format("Depassement age de %dans pour %s : %s",
				ageOverflow, session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
