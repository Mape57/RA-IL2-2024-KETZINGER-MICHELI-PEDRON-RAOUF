package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.TrainerJustification;

/**
 * Justification d'une contrainte de chevauchement de session d'un entraîneur.
 */
@Getter
public class TrainerSessionOverlappingJustification extends TrainerJustification {
	private final Session sessionA;
	private final Session sessionB;

	public TrainerSessionOverlappingJustification(Session sessionA, Session sessionB, HardSoftScore score) {
		this(sessionA, sessionB, score, getDescription(sessionA, sessionB, score));
	}

	public TrainerSessionOverlappingJustification(Session sessionA, Session sessionB, HardSoftScore score, String description) {
		super(sessionA.getTrainer(), score, description);
		this.sessionA = sessionA;
		this.sessionB = sessionB;
	}

	private static String getDescription(Session sessionA, Session sessionB, HardSoftScore score) {
		return String.format("Entrainer %s a deux sessions qui se chevauchent : %s et %s : %s",
				sessionA.getTrainer().getId(), sessionA, sessionB, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
