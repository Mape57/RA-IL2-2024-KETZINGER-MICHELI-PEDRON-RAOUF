package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de session avec des joueurs mais pas d'entraîneur.
 */
public record SessionWithPlayersNeedTrainerJustification(Session session, HardSoftScore score, String description) implements ConstraintJustification {
	public SessionWithPlayersNeedTrainerJustification(Session session, HardSoftScore score) {
		this(session, score, getDescription(session, score));
	}

	private static String getDescription(Session session, HardSoftScore score) {
		return String.format("Joueurs sans entraineur pour %s : %s",
				session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
