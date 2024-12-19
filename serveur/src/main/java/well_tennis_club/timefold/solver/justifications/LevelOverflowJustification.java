package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de différence de niveau dépassée.</br>
 * Sous le format : "La différence de niveau pour la session {information session} n'est pas respecté (dépasse de {levelOverflow} niveau(x))"
 */
public record LevelOverflowJustification(Session session, Integer levelOverflow,
										 String description) implements ConstraintJustification {
	public LevelOverflowJustification(Session session, Integer levelOverflow) {
		this(session, levelOverflow, getDescription(session, levelOverflow));
	}

	private static String getDescription(Session session, Integer levelOverflow) {
		return String.format("La différence de niveau pour la session du terrain '%s' le %s à %s n'est pas respecté (dépasse de %d niveau(s))",
				session.getTennisCourt(), session.getDayString(), session.getStartTime(), levelOverflow);
	}

	@Override
	public String toString() {
		return description;
	}
}
