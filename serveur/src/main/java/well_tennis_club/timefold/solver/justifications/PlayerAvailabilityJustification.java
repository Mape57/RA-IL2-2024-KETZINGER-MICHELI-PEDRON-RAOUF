package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de disponibilité de joueur non respectée.
 */
public record PlayerAvailabilityJustification(PlayerSessionLink playerSessionLink, HardSoftScore score,
											  String description) implements ConstraintJustification {
	public PlayerAvailabilityJustification(PlayerSessionLink playerSessionLink, HardSoftScore score) {
		this(playerSessionLink, score, getDescription(playerSessionLink, score));
	}

	private static String getDescription(PlayerSessionLink playerSessionLink, HardSoftScore score) {
		Session session = playerSessionLink.getSession();
		return String.format("Joueur %s indisponible pour %s : %s",
				playerSessionLink.getPlayer().getId(), session, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}