package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de disponibilité d'un entraîneur non respectée.
 */
@Getter
public class TrainerAvailabilityJustification extends SessionJustification {
	private final Integer sessionDuration;

	public TrainerAvailabilityJustification(Session session, Integer sessionDuration, HardSoftScore score) {
		this(session, sessionDuration, score, getDescription(session, sessionDuration, score));
	}

	public TrainerAvailabilityJustification(Session session, Integer sessionDuration, HardSoftScore score, String description) {
		super(session, score, description);
		this.sessionDuration = sessionDuration;
	}

	private static String getDescription(Session session, Integer sessionDuration, HardSoftScore score) {
		return String.format("L'entraîneur %s %s n'est pas disponible.",
				session.getTrainer().getName(), session.getTrainer().getSurname());
	}

	@Override
	public String toString() {
		return description;
	}

}