package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de différence d'âge dépassée.</br>
 * Sous le format : "La différence d'âge pour la session du terrain {session} n'est pas respecté (dépasse de {ageOverflow} ans) : {score}"
 */
public record AgeDifferenceJustification(Session session, Integer ageOverflow, HardSoftScore score,
										 String description) implements ConstraintJustification {
	public AgeDifferenceJustification(Session session, Integer ageOverflow, HardSoftScore score) {
		this(session, ageOverflow, score, getDescription(session, ageOverflow, score));
	}

	private static String getDescription(Session session, Integer ageOverflow, HardSoftScore score) {
		return String.format("La différence d'âge pour la session du terrain %s n'est pas respecté (dépasse de %d ans) : %s",
				session, ageOverflow, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
