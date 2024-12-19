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
 * Collecteur retournant la différence entre la différence d'âge réelle et la différence d'âge maximale autorisée.</br>
 * Soit : (âge max - âge min) - (différence d'âge maximale autorisée)
 */
public class AgeOverflowCollector implements UniConstraintCollector<PlayerSessionLink, List<Integer>, Integer> {
	private int maxAgeDifference = -1;

	@Override
	public @NonNull Supplier<List<Integer>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull BiFunction<List<Integer>, PlayerSessionLink, Runnable> accumulator() {
		return (ages, playerSessionLink) -> {
			Player player = playerSessionLink.getPlayer();
			if (ages.isEmpty()) maxAgeDifference = player.getSessionConstraint().ageDifference();
			Integer age = player.getAge();
			ages.add(age);
			return () -> ages.remove(age);
		};
	}

	@Override
	public @Nullable Function<List<Integer>, Integer> finisher() {
		return ages -> {
			ValueRange ageRange = new ValueRange(ages);
			return ageRange.size() - 1 - maxAgeDifference;
		};
	}
}
