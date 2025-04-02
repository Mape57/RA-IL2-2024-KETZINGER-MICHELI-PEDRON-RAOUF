package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de différence d'âge dépassée.
 */
@Getter
public class AgeDifferenceJustification extends SessionJustification {
	private final Integer ageOverflow;

	public AgeDifferenceJustification(Session session, Integer ageOverflow, HardSoftScore score, String description) {
		super(session, score, description);
		this.ageOverflow = ageOverflow;
	}

	public AgeDifferenceJustification(Session session, Integer ageOverflow, HardSoftScore score) {
		this(session, ageOverflow, score, getDescription(ageOverflow));
	}

	private static String getDescription(Integer ageOverflow) {
		return String.format("La différence d'âge du groupe est trop grande de %d ans.", ageOverflow);
	}

	@Override
	public String toString() {
		return description;
	}
}
