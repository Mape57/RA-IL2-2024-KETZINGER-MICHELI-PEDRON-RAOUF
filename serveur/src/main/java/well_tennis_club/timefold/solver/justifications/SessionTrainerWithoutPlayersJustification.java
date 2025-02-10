package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de session sans joueur.
 */
public record SessionTrainerWithoutPlayersJustification(Session session, HardSoftScore score,
														String description) implements ConstraintJustification {
	public SessionTrainerWithoutPlayersJustification(Session session, HardSoftScore score) {
		this(session, score, getDescription(session, score));
	}

	private static String getDescription(Session session, HardSoftScore score) {
		return String.format("Entraîneur sans joueur pour %s : %s",
				session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
