package well_tennis_club.timefold.solver.collectors;

import ai.timefold.solver.core.api.score.stream.uni.UniConstraintCollector;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import well_tennis_club.data_structure.ValueRange;
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
public class LevelOverflowCollector implements UniConstraintCollector<PlayerSessionLink, List<Integer>, Integer> {
	private int maxLevelDifference = -1;

	@Override
	public @NonNull Supplier<List<Integer>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull BiFunction<List<Integer>, PlayerSessionLink, Runnable> accumulator() {
		return (levels, playerSessionLink) -> {
			Player player = playerSessionLink.getPlayer();
			if (maxLevelDifference == -1) maxLevelDifference = player.getSessionConstraint().levelDifference();
			Integer level = player.getLevel();
			levels.add(level);
			return () -> levels.remove(level);
		};
	}

	@Override
	public @Nullable Function<List<Integer>, Integer> finisher() {
		return ages -> {
			ValueRange levelRange = new ValueRange(ages);
			return levelRange.size() - 1 - maxLevelDifference;
		};
	}
}
