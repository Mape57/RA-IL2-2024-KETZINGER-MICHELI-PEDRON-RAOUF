package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de taille de groupe dépassée.</br>
 * Sous le format : "La différence de taille de groupe pour la session du terrain {session} n'est pas respecté (dépasse de {groupSizeDifference} joueurs) : {score}"
 */
public record GroupSizeJustification(Session session, Integer groupSizeDifference, HardSoftScore score,
									 String description) implements ConstraintJustification {
	public GroupSizeJustification(Session session, Integer ageOverflow, HardSoftScore score) {
		this(session, ageOverflow, score, getDescription(session, ageOverflow, score));
	}

	private static String getDescription(Session session, Integer groupSizeDifference, HardSoftScore score) {
		return String.format("La différence de taille de groupe pour la session du terrain %s n'est pas respecté (dépasse de %d joueurs) : %s",
				session, groupSizeDifference, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
