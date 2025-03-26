package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification du regroupement par joueur de même contrainte de session.
 */
@Getter
public class GroupBySessionConstraintJustification extends SessionJustification {
	private final Integer distinctSessionConstraint;

	public GroupBySessionConstraintJustification(Session session, Integer distinctSessionConstraint, HardSoftScore score) {
		this(session, distinctSessionConstraint, score, getDescription(distinctSessionConstraint));
	}

	public GroupBySessionConstraintJustification(Session session, Integer distinctSessionConstraint, HardSoftScore score, String description) {
		super(session, score, description);
		this.distinctSessionConstraint = distinctSessionConstraint;
	}

	private static String getDescription(Integer distinctSessionConstraint) {
		return String.format("Les joueurs font partis de %d groupes d'âge différents", distinctSessionConstraint);
	}

	@Override
	public String toString() {
		return description;
	}
}
