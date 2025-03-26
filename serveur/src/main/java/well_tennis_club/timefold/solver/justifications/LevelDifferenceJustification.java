package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de différence de niveau dépassée.
 */
@Getter
public class LevelDifferenceJustification extends SessionJustification {
	private final Integer levelOverflow;

	public LevelDifferenceJustification(Session session, Integer levelOverflow, HardSoftScore score) {
		this(session, levelOverflow, score, getDescription(levelOverflow));
	}

	public LevelDifferenceJustification(Session session, Integer levelOverflow, HardSoftScore score, String description) {
		super(session, score, description);
		this.levelOverflow = levelOverflow;
	}

	private static String getDescription(Integer levelOverflow) {
		return String.format("La différence de niveau du groupe est trop grande de %d niveaux.", levelOverflow);
	}

	@Override
	public String toString() {
		return description;
	}
}
