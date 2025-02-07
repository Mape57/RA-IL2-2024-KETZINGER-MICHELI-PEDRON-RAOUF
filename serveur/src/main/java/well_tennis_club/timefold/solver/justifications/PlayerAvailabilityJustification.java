package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;

/**
 * Justification d'une contrainte de disponibilité de joueur non respectée.</br>
 * Sous le format : "Le joueur {nom joueur} n'est pas disponible pour la session du terrain {session} : {score}"
 */
public record PlayerAvailabilityJustification(PlayerSessionLink playerSessionLink, HardSoftScore score, String description) implements ConstraintJustification {
	public PlayerAvailabilityJustification(PlayerSessionLink playerSessionLink, HardSoftScore score) {
		this(playerSessionLink, score, getDescription(playerSessionLink, score));
	}

	private static String getDescription(PlayerSessionLink playerSessionLink, HardSoftScore score) {
		Session session = playerSessionLink.getSession();
		return String.format("Le joueur %s n'est pas disponible pour la session du terrain %s : %s",
				playerSessionLink.getPlayer().getName(), session, score.toString());
		}

	@Override
	public String toString() {
		return description;
	}
}