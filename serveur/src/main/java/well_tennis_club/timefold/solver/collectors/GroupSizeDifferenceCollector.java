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
 * Collecteur retournant la différence entre la taille du groupe réel et la taille autorisée.</br>
 * Soit : 0 si la taille réelle est comprise entre la taille minimum et la taille maximum</br>
 * Soit : -x si la taille réelle est inférieure à la taille minimum, x étant la différence</br>
 * Soit : x si la taille réelle est supérieure à la taille maximum, x étant la différence
 */
public class GroupSizeDifferenceCollector implements UniConstraintCollector<PlayerSessionLink, List<Player>, Integer> {
	private ValueRange maxGroupSize;

	@Override
	public @NonNull Supplier<List<Player>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull BiFunction<List<Player>, PlayerSessionLink, Runnable> accumulator() {
		return (players, playerSessionLink) -> {
			Player player = playerSessionLink.getPlayer();
			if (maxGroupSize == null) maxGroupSize = player.getSessionConstraint().groupSize();
			players.add(player);
			return () -> players.remove(player);
		};
	}

	@Override
	public @Nullable Function<List<Player>, Integer> finisher() {
		return players -> maxGroupSize.difference(players.size());
	}
}
