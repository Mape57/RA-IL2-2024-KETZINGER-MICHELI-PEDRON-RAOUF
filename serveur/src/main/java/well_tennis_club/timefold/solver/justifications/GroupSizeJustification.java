package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de taille de groupe dépassée.
 */
@Getter
public class GroupSizeJustification extends SessionJustification {
	private final Integer groupSizeDifference;

	public GroupSizeJustification(Session session, Integer groupSizeDifference, HardSoftScore score) {
		this(session, groupSizeDifference, score, getDescription(groupSizeDifference));
	}

	public GroupSizeJustification(Session session, Integer groupSizeDifference, HardSoftScore score, String description) {
		super(session, score, description);
		this.groupSizeDifference = groupSizeDifference;
	}

	private static String getDescription(Integer groupSizeDifference) {
		return String.format("%d joueurs en trop dans le groupe.", groupSizeDifference);
	}

	@Override
	public String toString() {
		return description;
	}
}
