package well_tennis_club.timefold.solver.collectors;

import ai.timefold.solver.core.api.score.stream.uni.UniConstraintCollector;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import well_tennis_club.timefold.data_structure.ValueRange;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.PlayerSessionLink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Collecteur retournant la différence entre la différence de niveau réelle et la différence de niveau maximale autorisée.</br>
 * Soit : (niveau max - niveau min) - (différence de niveau maximale autorisée)
 */
public class LevelOverflowCollector implements UniConstraintCollector<PlayerSessionLink, List<Player>, Integer> {
	@Override
	public @NonNull Supplier<List<Player>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull BiFunction<List<Player>, PlayerSessionLink, Runnable> accumulator() {
		return (players, playerSessionLink) -> {
			Player player = playerSessionLink.getPlayer();
			players.add(player);
			return () -> players.remove(player);
		};
	}

	@Override
	public @Nullable Function<List<Player>, Integer> finisher() {
		return players -> {
			List<Integer> levels = players.stream().map(Player::getLevel).toList();
			ValueRange levelRange = new ValueRange(levels);
			int maxLevelDifference = players.getFirst().getSessionConstraint().levelDifference();
			return levelRange.size() - 1 - maxLevelDifference;
		};
	}
}
