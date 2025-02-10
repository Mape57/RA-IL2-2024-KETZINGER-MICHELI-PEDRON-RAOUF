package well_tennis_club.timefold.solver.collectors;

import ai.timefold.solver.core.api.function.TriFunction;
import ai.timefold.solver.core.api.score.stream.bi.BiConstraintCollector;
import org.jspecify.annotations.NonNull;
import well_tennis_club.timefold.data_structure.ValueRange;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.domain.Trainer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Collecteur retournant une plage représentant les âges des joueurs d'un groupe.
 */
public class TrainerAgeOverflowCollector implements BiConstraintCollector<Session, PlayerSessionLink, List<PlayerSessionLink>, Integer> {
	@Override
	public @NonNull Supplier<List<PlayerSessionLink>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull TriFunction<List<PlayerSessionLink>, Session, PlayerSessionLink, Runnable> accumulator() {
		return (psls, session, playerSessionLink) -> {
			psls.add(playerSessionLink);
			return () -> psls.remove(playerSessionLink);
		};
	}

	@Override
	public @NonNull Function<List<PlayerSessionLink>, Integer> finisher() {
		return psls -> {
			Trainer trainer = psls.getFirst().getSession().getTrainer();
			if (trainer == null) return 0;
			ValueRange maxGroupAge = trainer.getAgePreference();

			List<Integer> playersAge = psls.stream().map(psl -> psl.getPlayer().getAge()).toList();
			ValueRange groupAge = new ValueRange(playersAge);

			return maxGroupAge.difference(groupAge.getMax()) + maxGroupAge.difference(groupAge.getMin());
		};
	}
}
