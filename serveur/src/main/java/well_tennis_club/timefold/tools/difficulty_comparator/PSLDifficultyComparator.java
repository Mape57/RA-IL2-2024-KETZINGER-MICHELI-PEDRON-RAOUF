package well_tennis_club.timefold.tools.difficulty_comparator;

import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.PlayerSessionLink;

import java.time.Duration;
import java.util.Comparator;

/**
 * Classe permettant uniquement de à Timefold
 */
public class PSLDifficultyComparator implements Comparator<PlayerSessionLink> {
	public int compare(PlayerSessionLink a, PlayerSessionLink b) {
		return 0; // (int) totalAvailability(b.getPlayer()).minus(totalAvailability(a.getPlayer())).toMinutes();
	}

	private Duration totalAvailability(Player player) {
		Duration total = Duration.ZERO;
		for (Timeslot timeslot : player.getAvailability()) {
			total = total.plus(timeslot.duration());
		}
		return total;
	}
}
