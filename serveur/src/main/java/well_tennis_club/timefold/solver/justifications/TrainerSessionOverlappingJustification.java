package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de chevauchement de session d'un entraîneur.
 */
public record TrainerSessionOverlappingJustification(Session sessionA, Session sessionB, HardSoftScore score,
													 String description) implements ConstraintJustification {
	public TrainerSessionOverlappingJustification(Session sessionA, Session sessionB, HardSoftScore score) {
		this(sessionA, sessionB, score, getDescription(sessionA, sessionB, score));
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
