package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.solver.justifications.groupe.SessionJustification;

/**
 * Justification d'une contrainte de disponibilité de joueur non respectée.
 */
@Getter
public class PlayerAvailabilityJustification extends SessionJustification {
	private Player player;

	public PlayerAvailabilityJustification(PlayerSessionLink playerSessionLink, HardSoftScore score) {
		super(playerSessionLink.getSession(), score, getDescription(playerSessionLink));
		this.player = playerSessionLink.getPlayer();
	}

	private static String getDescription(PlayerSessionLink playerSessionLink) {
		return String.format("Le joueur %s %s n'est pas disponible.",
				playerSessionLink.getPlayer().getName(), playerSessionLink.getPlayer().getSurname());
	}

	@Override
	public String toString() {
		return description;
	}
}