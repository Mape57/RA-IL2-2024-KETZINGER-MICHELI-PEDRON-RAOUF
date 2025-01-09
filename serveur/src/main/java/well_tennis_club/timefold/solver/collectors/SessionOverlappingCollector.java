package well_tennis_club.timefold.solver.collectors;

import ai.timefold.solver.core.api.function.TriFunction;
import ai.timefold.solver.core.api.score.stream.bi.BiConstraintCollector;
import ai.timefold.solver.core.impl.score.stream.collector.IntCounter;
import org.jspecify.annotations.NonNull;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;

import java.time.LocalTime;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class SessionOverlappingCollector implements BiConstraintCollector<PlayerSessionLink, PlayerSessionLink, IntCounter, Integer> {
	@Override
	public @NonNull Supplier<IntCounter> supplier() {
		return IntCounter::new;
	}

	@Override
	public @NonNull TriFunction<IntCounter, PlayerSessionLink, PlayerSessionLink, Runnable> accumulator() {
		return (counter, pslA, pslB) -> {
			Session sessionA = pslA.getSession();
			Session sessionB = pslB.getSession();

			if (!Objects.equals(sessionA.getTennisCourt(), sessionB.getTennisCourt())) return () -> {};
			if (!Objects.equals(sessionA.getDay(), sessionB.getDay())) return () -> {};

			LocalTime sessionAStartTime = sessionA.getStartTime();
			LocalTime sessionBStartTime = sessionB.getStartTime();
			if (!sessionAStartTime.isBefore(sessionBStartTime)) return () -> {};

			int sessionADuration = pslA.getPlayer().getSessionConstraint().duration();
			LocalTime sessionAEndTime = sessionAStartTime.plusMinutes(sessionADuration);
			if (!sessionBStartTime.isBefore(sessionAEndTime)) return () -> {};

			counter.increment();
			return counter::decrement;
		};
	}

	@Override
	public @NonNull Function<IntCounter, Integer> finisher() {
		return IntCounter::result;
	}
}
