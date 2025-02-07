package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de différence d'âge dépassée.</br>
 * Sous le format : "La différence d'âge pour la session {information session} n'est pas respecté (dépasse de {ageOverflow} ans)"
 */
public record AgeOverflowJustification(Session session, Integer ageOverflow,
									   String description) implements ConstraintJustification {
	public AgeOverflowJustification(Session session, Integer ageOverflow) {
		this(session, ageOverflow, getDescription(session, ageOverflow));
	}

	private static String getDescription(Session session, Integer ageOverflow) {
		return String.format("La différence d'âge pour la session du terrain '%s' le %s à %s n'est pas respecté (dépasse de %d ans)",
				session.getTennisCourt(), session.getDayString(), session.getStartTime(), ageOverflow);
	}

	@Override
	public String toString() {
		return description;
	}
}
