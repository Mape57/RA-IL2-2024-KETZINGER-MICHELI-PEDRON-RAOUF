package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de session avec des joueurs mais pas d'entraîneur.
 */
@Getter
public class SessionWithPlayersNeedTrainerJustification extends SessionJustification {
	public SessionWithPlayersNeedTrainerJustification(Session session, HardSoftScore score) {
		this(session, score, "Pas d'entraîneur pour la session.");
	}

	public SessionWithPlayersNeedTrainerJustification(Session session, HardSoftScore score, String description) {
		super(session, score, description);
	}

	@Override
	public String toString() {
		return description;
	}
}
