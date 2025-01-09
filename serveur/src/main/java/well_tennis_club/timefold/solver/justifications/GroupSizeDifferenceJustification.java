package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de taille de groupe dépassée.</br>
 * Sous le format : "La différence de taille de groupe pour la session {information session} n'est pas respecté (dépasse de {groupSizeDifference} joueurs)"
 */
public record GroupSizeDifferenceJustification(Session session, Integer groupSizeDifference,
											   String description) implements ConstraintJustification {
	public GroupSizeDifferenceJustification(Session session, Integer ageOverflow) {
		this(session, ageOverflow, getDescription(session, ageOverflow));
	}

	private static String getDescription(Session session, Integer groupSizeDifference) {
		return String.format("La différence de taille de groupe pour la session du terrain '%s' le %s à %s n'est pas respecté (dépasse de %d joueurs)",
				session.getTennisCourt(), session.getDayString(), session.getStartTime(), groupSizeDifference);
	}

	@Override
	public String toString() {
		return description;
	}
}
