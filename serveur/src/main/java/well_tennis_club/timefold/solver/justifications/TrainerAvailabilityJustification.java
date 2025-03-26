package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.TrainerJustification;

/**
 * Justification d'une contrainte de disponibilité d'un entraîneur non respectée.
 */
@Getter
public class TrainerAvailabilityJustification extends TrainerJustification {
	private final Session session;
	private final Integer sessionDuration;

	public TrainerAvailabilityJustification(Session session, Integer sessionDuration, HardSoftScore score) {
		this(session, sessionDuration, score, getDescription(session, sessionDuration, score));
	}

	public TrainerAvailabilityJustification(Session session, Integer sessionDuration, HardSoftScore score, String description) {
		super(session.getTrainer(), score, description);
		this.session = session;
		this.sessionDuration = sessionDuration;
	}

	private static String getDescription(Session session, Integer sessionDuration, HardSoftScore score) {
		return String.format("Entraineur %s indisponible pour %s : %s",
				session.getTrainer().getId(), session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}

}