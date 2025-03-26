package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.PlayerJustification;

import java.util.List;

/**
 * Justification d'une contrainte de session unique par jour dépassée.
 */
@Getter
public class PlayerSingleSessionPerDayJustification extends PlayerJustification {
	private final List<Session> sessions;

	public PlayerSingleSessionPerDayJustification(Player player, List<Session> sessions, HardSoftScore score) {
		super(player, score, getDescription(player, sessions, score));
		this.sessions = sessions;
	}

	private static String getDescription(Player player, List<Session> sessions, HardSoftScore score) {
		return String.format("%s a plusieurs cours le %s : %s",
				player.getId(), sessions, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
