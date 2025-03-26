package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de session sans joueur.
 */
@Getter
public class SessionTrainerWithoutPlayersJustification implements ConstraintJustification {
	private Session session;
	private HardSoftScore score;
	private String description;

	public SessionTrainerWithoutPlayersJustification(Session session, HardSoftScore score) {
		this(session, score, getDescription(session, score));
	}

	public SessionTrainerWithoutPlayersJustification(Session session, HardSoftScore score, String description) {
		this.session = session;
		this.score = score;
		this.description = description;
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
