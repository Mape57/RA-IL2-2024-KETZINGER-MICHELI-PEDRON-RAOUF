package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de chevauchement de session.</br>
 * Sous le format : "La session du terrain {session} est en chevauchement avec {sessionOverlapping} autres sessions : {score}"
 */
public record SessionOverlapJustification(Session session, Integer sessionOverlapping, HardSoftScore score,
										  String description) implements ConstraintJustification {
	public SessionOverlapJustification(Session session, Integer sessionOverlapping, HardSoftScore score) {
		this(session, sessionOverlapping, score, getDescription(session, sessionOverlapping, score));
	}

	private static String getDescription(Session session, Integer sessionOverlapping, HardSoftScore score) {
		return String.format("La session du terrain %s est en chevauchement avec %d autres sessions : %s",
				session, sessionOverlapping, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
