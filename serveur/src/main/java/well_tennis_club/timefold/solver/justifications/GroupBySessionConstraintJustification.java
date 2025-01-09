package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification du regroupement par joueur de même contrainte de session.</br>
 * Sous le format : "La session du terrain {session} possède des joueurs n'ayant pas les mêmes contraintes de session ({distinctSessionConstraint} distinctes) : {score}"
 */
public record GroupBySessionConstraintJustification(Session session, Integer distinctSessionConstraint,
													HardSoftScore score,
													String description) implements ConstraintJustification {
	public GroupBySessionConstraintJustification(Session session, Integer distinctSessionConstraint, HardSoftScore score) {
		this(session, distinctSessionConstraint, score, getDescription(session, distinctSessionConstraint, score));
	}

	private static String getDescription(Session session, Integer distinctSessionConstraint, HardSoftScore score) {
		return String.format("La session du terrain %s possède des joueurs n'ayant pas les mêmes contraintes de session (%d distinctes) : %s",
				session, distinctSessionConstraint, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
