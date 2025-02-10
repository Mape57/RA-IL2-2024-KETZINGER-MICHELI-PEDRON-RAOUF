package well_tennis_club.timefold.solver.collectors;

import ai.timefold.solver.core.api.function.TriFunction;
import ai.timefold.solver.core.api.score.stream.bi.BiConstraintCollector;
import org.jspecify.annotations.NonNull;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Collecteur retournant la durée de la session en fonction des joueurs présents.
 */
public class SessionDurationCollector implements BiConstraintCollector<Session, PlayerSessionLink, List<Player>, Integer> {
	@Override
	public @NonNull Supplier<List<Player>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull TriFunction<List<Player>, Session, PlayerSessionLink, Runnable> accumulator() {
		return (players, session, playerSessionLink) -> {
			Player player = playerSessionLink.getPlayer();
			players.add(player);
			return () -> players.remove(player);
		};
	}

	@Override
	public @NonNull Function<List<Player>, Integer> finisher() {
		return players -> players.getFirst().getSessionConstraint().duration();
	}
}
