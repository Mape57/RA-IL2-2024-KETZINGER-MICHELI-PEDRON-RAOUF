package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification du regroupement par joueur de même contrainte de session.
 */
public record GroupBySessionConstraintJustification(Session session, Integer distinctSessionConstraint,
													HardSoftScore score,
													String description) implements ConstraintJustification {
	public GroupBySessionConstraintJustification(Session session, Integer distinctSessionConstraint, HardSoftScore score) {
		this(session, distinctSessionConstraint, score, getDescription(session, distinctSessionConstraint, score));
	}

	private static String getDescription(Session session, Integer distinctSessionConstraint, HardSoftScore score) {
		return String.format("Trop de type de contrainte de session (%d) pour %s : %s",
				distinctSessionConstraint, session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
