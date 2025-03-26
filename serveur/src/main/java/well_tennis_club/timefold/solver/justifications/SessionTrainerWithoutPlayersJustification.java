package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.TrainerJustification;

/**
 * Justification d'une contrainte de session sans joueur.
 */
@Getter
public class SessionTrainerWithoutPlayersJustification extends TrainerJustification {
	private Session session;

	public SessionTrainerWithoutPlayersJustification(Session session, HardSoftScore score) {
		this(session, score, getDescription(session, score));
	}

	public SessionTrainerWithoutPlayersJustification(Session session, HardSoftScore score, String description) {
		super(session.getTrainer(), score, description);
		this.session = session;
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
