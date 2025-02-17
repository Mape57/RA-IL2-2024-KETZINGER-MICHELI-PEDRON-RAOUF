package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de différence de niveau dépassée.
 */
public record LevelDifferenceJustification(Session session, Integer levelOverflow, HardSoftScore score,
										   String description) implements ConstraintJustification {
	public LevelDifferenceJustification(Session session, Integer levelOverflow, HardSoftScore score) {
		this(session, levelOverflow, score, getDescription(session, levelOverflow, score));
	}

	private static String getDescription(Session session, Integer levelOverflow, HardSoftScore score) {
		return String.format("Dépassement de niveau (%d) pour %s : %s",
				levelOverflow, session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
