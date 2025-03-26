package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de préférence d'âge d'un entraîneur non respectée.
 */
@Getter
public class TrainerPreferedAgeJustification extends SessionJustification {
	private final Integer trainerAgeOverflow;

	public TrainerPreferedAgeJustification(Session session, Integer trainerAgeOverflow, HardSoftScore score) {
		this(session, trainerAgeOverflow, score, "Les préférences d'âge de l'entraîneur ne sont pas respectées.");
	}

	public TrainerPreferedAgeJustification(Session session, Integer trainerAgeOverflow, HardSoftScore score, String description) {
		super(session, score, description);
		this.trainerAgeOverflow = trainerAgeOverflow;
	}

	@Override
	public String toString() {
		return description;
	}
}
