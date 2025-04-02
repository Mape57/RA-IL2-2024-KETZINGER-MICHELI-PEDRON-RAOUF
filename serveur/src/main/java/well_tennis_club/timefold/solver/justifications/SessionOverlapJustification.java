package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de chevauchement de session.
 */
@Getter
public class SessionOverlapJustification extends SessionJustification {
	private final Integer sessionOverlapping;

	public SessionOverlapJustification(Session session, Integer sessionOverlapping, HardSoftScore score) {
		this(session, sessionOverlapping, score, getDescription(sessionOverlapping));
	}

	public SessionOverlapJustification(Session session, Integer sessionOverlapping, HardSoftScore score, String description) {
		super(session, score, description);
		this.sessionOverlapping = sessionOverlapping;
	}

	private static String getDescription(Integer sessionOverlapping) {
		// FIXME indiquer les sessions en chevauchement plutôt que le nombre
		return String.format("Chevauchement de %d session(s)",
				sessionOverlapping);
	}

	@Override
	public String toString() {
		return description;
	}
}
