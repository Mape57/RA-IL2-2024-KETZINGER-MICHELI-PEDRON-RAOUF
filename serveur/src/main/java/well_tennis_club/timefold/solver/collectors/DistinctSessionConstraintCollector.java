package well_tennis_club.timefold.solver.collectors;

import ai.timefold.solver.core.api.score.stream.uni.UniConstraintCollector;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.domain.PlayerSessionLink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Collecteur retournant le nombre de contraintes de session distinctes d'un groupe de joueurs.
 */
public class DistinctSessionConstraintCollector implements UniConstraintCollector<PlayerSessionLink, List<SessionConstraint>, Integer> {
	@Override
	public @NonNull Supplier<List<SessionConstraint>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull BiFunction<List<SessionConstraint>, PlayerSessionLink, Runnable> accumulator() {
		return (sessionConstraints, playerSessionLink) -> {
			SessionConstraint sessionConstraint = playerSessionLink.getPlayer().getSessionConstraint();
			sessionConstraints.add(sessionConstraint);
			return () -> sessionConstraints.remove(sessionConstraint);
		};
	}

	@Override
	public @Nullable Function<List<SessionConstraint>, Integer> finisher() {
		return sessionConstraints -> {
			int differentSessionConstraints = 1;
			for (SessionConstraint sessionConstraint : sessionConstraints) {
				if (!sessionConstraint.equals(sessionConstraints.get(0))) {
					differentSessionConstraints++;
				}
			}
			return differentSessionConstraints;
		};
	}
}
