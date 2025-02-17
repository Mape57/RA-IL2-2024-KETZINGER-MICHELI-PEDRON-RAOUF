package well_tennis_club.timefold.solver.collectors;

import ai.timefold.solver.core.api.function.TriFunction;
import ai.timefold.solver.core.api.score.stream.bi.BiConstraintCollector;
import org.jspecify.annotations.NonNull;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.domain.Trainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Collecteur retournant une plage représentant les âges des joueurs d'un groupe.
 */
public class TrainerweeklyMinutesCollector implements BiConstraintCollector<Trainer, PlayerSessionLink, List<PlayerSessionLink>, Integer> {
	@Override
	public @NonNull Supplier<List<PlayerSessionLink>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull TriFunction<List<PlayerSessionLink>, Trainer, PlayerSessionLink, Runnable> accumulator() {
		return (psls, trainer, psl) -> {
			psls.add(psl);
			return () -> psls.remove(psl);
		};
	}

	@Override
	public @NonNull Function<List<PlayerSessionLink>, Integer> finisher() {
		return psls -> {
			Map<Session, Integer> sessionHours = new HashMap<>();
			for (PlayerSessionLink psl : psls) {
				Session session = psl.getSession();
				int duration = psl.getPlayer().getSessionConstraint().duration();
				sessionHours.putIfAbsent(session, duration);
			}
			return sessionHours.values().stream().mapToInt(Integer::intValue).sum();
		};
	}

}
