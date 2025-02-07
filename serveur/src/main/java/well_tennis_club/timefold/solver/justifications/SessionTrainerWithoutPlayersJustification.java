package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de session sans joueur.</br>
 * Sous le format : "L'entraîneur de la session du terrain {session} n'a pas de joueur. : {score}"
 */
public record SessionTrainerWithoutPlayersJustification(Session session, HardSoftScore score,
														String description) implements ConstraintJustification {
	public SessionTrainerWithoutPlayersJustification(Session session, HardSoftScore score) {
		this(session, score, getDescription(session, score));
	}

	private static String getDescription(Session session, HardSoftScore score) {
		return String.format("L'entraîneur de la session du terrain %s n'a pas de joueur. : %s",
				session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
