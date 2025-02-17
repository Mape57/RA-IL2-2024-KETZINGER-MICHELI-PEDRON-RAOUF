package well_tennis_club.timefold.solver.justifications;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.ConstraintJustification;
import well_tennis_club.timefold.domain.Player;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Justification d'une contrainte de session unique par jour dépassée.
 */
public record PlayerSingleSessionPerDayJustification(Player player, List<DayOfWeek> days, HardSoftScore score, String description) implements ConstraintJustification {
	public PlayerSingleSessionPerDayJustification(Player player, List<DayOfWeek> days, HardSoftScore score) {
		this(player, days, score, getDescription(player, days, score));
	}

	private static String getDescription(Player player, List<DayOfWeek> days, HardSoftScore score) {
		return String.format("%s a plusieurs cours le %s : %s",
				player.getId(), days, score.toString());
	}

	@Override
	public String toString() {
		return description;
	}
}
