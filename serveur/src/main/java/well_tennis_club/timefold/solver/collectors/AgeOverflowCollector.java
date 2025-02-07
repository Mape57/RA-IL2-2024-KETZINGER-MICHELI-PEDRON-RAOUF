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
 * Collecteur retournant la différence entre la différence d'âge réelle et la différence d'âge maximale autorisée.</br>
 * Soit : (âge max - âge min) - (différence d'âge maximale autorisée)
 */
public class AgeOverflowCollector implements UniConstraintCollector<PlayerSessionLink, List<Player>, Integer> {
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
			List<Integer> ages = players.stream().map(Player::getAge).toList();
			ValueRange ageRange = new ValueRange(ages);
			int maxAgeDifference = players.getFirst().getSessionConstraint().ageDifference();
			return ageRange.size() - 1 - maxAgeDifference;
		};
	}
}
