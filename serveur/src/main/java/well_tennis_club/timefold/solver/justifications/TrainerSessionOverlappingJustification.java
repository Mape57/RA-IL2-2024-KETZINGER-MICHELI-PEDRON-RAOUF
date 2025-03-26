package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionsJustification;

import java.util.List;

/**
 * Justification d'une contrainte de chevauchement de session d'un entraîneur.
 */
@Getter
public class TrainerSessionOverlappingJustification extends SessionsJustification {
	public TrainerSessionOverlappingJustification(Session sessionA, Session sessionB, HardSoftScore score) {
		this(sessionA, sessionB, score, getDescription(sessionA, sessionB, score));
	}

	public TrainerSessionOverlappingJustification(Session sessionA, Session sessionB, HardSoftScore score, String description) {
		super(List.of(sessionA, sessionB), score, description);
	}

	private static String getDescription(Session sessionA, Session sessionB, HardSoftScore score) {
		return String.format("L'entraîneur %s %s doit être présent sur deux cours en même temps %s et %s.",
				sessionA.getTrainer().getName(), sessionA.getTrainer().getSurname(), sessionA.getTennisCourtName(), sessionB.getTennisCourtName());
	}

	@Override
	public String toString() {
		return description;
	}
}
