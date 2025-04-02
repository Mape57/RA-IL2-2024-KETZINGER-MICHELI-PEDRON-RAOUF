package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.justifications.groupe.SessionsJustification;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Justification d'une contrainte de session unique par jour dépassée.
 */
@Getter
public class PlayerSingleSessionPerDayJustification extends SessionsJustification {
	private final Player player;

	public PlayerSingleSessionPerDayJustification(Player player, List<Session> sessions, HardSoftScore score) {
		super(sessions, score, getDescription(player, sessions));
		this.player = player;
	}

	private static String getDescription(Player player, List<Session> sessions) {
		return String.format("Le joueur %s %s a plusieurs cours le même jour : %s.",
				player.getName(), player.getSurname(), sessions.stream().map(Session::toString).collect(Collectors.joining(", ")));
	}

	@Override
	public String toString() {
		return description;
	}
}
