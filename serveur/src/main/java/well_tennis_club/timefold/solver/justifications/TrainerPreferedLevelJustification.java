package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de préférence de niveau d'un entraîneur non respectée.
 */
@Getter
public class TrainerPreferedLevelJustification extends SessionJustification {
	private final Integer trainerLevelOverflow;

	public TrainerPreferedLevelJustification(Session session, Integer trainerLevelOverflow, HardSoftScore score) {
		this(session, trainerLevelOverflow, score, "Les préférences de niveau de l'entraîneur ne sont pas respectées.");
	}

	public TrainerPreferedLevelJustification(Session session, Integer trainerLevelOverflow, HardSoftScore score, String description) {
		super(session, score, description);
		this.trainerLevelOverflow = trainerLevelOverflow;
	}

	@Override
	public String toString() {
		return description;
	}
}
